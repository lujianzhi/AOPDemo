package com.ian.modulea;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;

/**
 * USER：lujianzhi
 * DATE：2022/12/7
 */
public abstract class MyAsmFactory implements AsmClassVisitorFactory<InstrumentationParameters.None> {

    @NotNull
    @Override
    public ClassVisitor createClassVisitor(@NotNull ClassContext classContext, @NotNull ClassVisitor classVisitor) {
        return new MyClassVisitor(classVisitor);
    }

    @Override
    public boolean isInstrumentable(@NotNull ClassData classData) {
        return true;
    }
}
