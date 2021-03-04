package com.test.asm;

import org.objectweb.asm.*;
import org.springframework.core.ResolvableType;

import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author lixiaoyu
 * @since 2021/3/4
 */
public class TestAsm {
    public static void main(String[] args) throws Exception {
        ClassReader classReader = new ClassReader("com.test.asm.TestBean");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor adapter = new ClassSysOutAdapter(classWriter);
        classReader.accept(adapter, 0);
        byte[] bytes = classWriter.toByteArray();
        Files.write(Paths.get("/Users/lixiaoyu/code/my_group/guava-test/target/classes/com/test/asm/TestBean.class"), bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
//        Class<?> aClass = Class.forName("com.test.asm.TestAsm");
//        aClass.getDeclaredMethod("sout").invoke(aClass.newInstance(), null);
    }

    static class ClassSysOutAdapter extends ClassVisitor {

        private ClassVisitor cv;

        public ClassSysOutAdapter(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
            this.cv = cv;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
            if("sout".equals(name)) {
                return new MethodSysOutAdapter(methodVisitor);
            }
            return methodVisitor;
        }
    }

    static class MethodSysOutAdapter extends MethodVisitor {

        private MethodVisitor methodVisitor;

        public MethodSysOutAdapter(MethodVisitor mv) {
            super(Opcodes.ASM5, mv);
            this.methodVisitor = mv;
        }

        @Override
        public void visitCode() {
//            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitLdcInsn("another asm");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
//            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();

        }
    }
}
