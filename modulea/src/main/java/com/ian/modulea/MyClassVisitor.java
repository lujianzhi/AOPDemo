package com.ian.modulea;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * USER：lujianzhi
 * DATE：2022/12/7
 */
class MyClassVisitor extends ClassVisitor {
    //ClassVisitor可以帮助我们访问 .class文件
    public MyClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        MethodVisitor visitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        // AdviceAdapter 是 MethodVisitor 的子类，使用 AdviceAdapter 可以更方便的修改方法的字节码。
        // AdviceAdapter其中几个重要方法如下：
        // void visitCode()：表示 ASM 开始扫描这个方法
        // void onMethodEnter()：进入这个方法
        // void onMethodExit()：即将从这个方法出去
        // void onVisitEnd()：表示方法扫描完毕
        MethodVisitor newVisitor = new AdviceAdapter(Opcodes.ASM5, visitor, access, name, descriptor) {

            int param1;

            @Override
            protected void onMethodEnter() {
                System.out.println(
                        "onMethodEnter -> " +
                                "visitor:" + visitor + "; access:" + access + "; name:" + name + "; descriptor:" + descriptor);
                param1 = newLocal(Type.LONG_TYPE);
                visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                visitVarInsn(LSTORE, param1);

                visitLdcInsn("ian");
                visitTypeInsn(NEW, "java/lang/StringBuilder");
                visitInsn(DUP);
                visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                visitLdcInsn(name + " - enterTime:");
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)" +
                        "Ljava/lang/StringBuilder;", false);
                visitVarInsn(LLOAD, param1);
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;",
                        false);
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I",
                        false);
                visitInsn(POP);
                super.onMethodEnter();
            }

            @Override
            protected void onMethodExit(int opcode) {
                System.out.println(
                        "onMethodExit -> " +
                                "visitor:" + visitor + "; access:" + access + "; name:" + name + "; descriptor:" + descriptor + "; opcode:" + opcode);
                int param2 = newLocal(Type.LONG_TYPE);
                visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                visitVarInsn(LSTORE, param2);

                visitLdcInsn("ian");
                visitTypeInsn(NEW, "java/lang/StringBuilder");
                visitInsn(DUP);
                visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                visitLdcInsn(name + " - durationTime:");
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)" +
                        "Ljava/lang/StringBuilder;", false);
                visitVarInsn(LLOAD, param2);
                visitVarInsn(LLOAD, param1);
                visitInsn(LSUB);
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;",
                        false);
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I",
                        false);
                visitInsn(POP);
                super.onMethodExit(opcode);
            }
        };
        return newVisitor;
    }
}
