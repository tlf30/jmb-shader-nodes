package com.ss.editor.shader.nodes.ui.component.shader.nodes.operation.attach;

import com.jme3.shader.ShaderNode;
import com.jme3.shader.VariableMapping;
import com.ss.editor.annotation.JmeThread;
import com.ss.editor.shader.nodes.ui.component.editor.ShaderNodesChangeConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The implementation of attaching an attribute to a vertex shader nodes.
 *
 * @author JavaSaBr
 */
public class AttachAttributeToShaderNodeOperation extends AttachShaderNodeOperation {

    public AttachAttributeToShaderNodeOperation(@NotNull final ShaderNode shaderNode,
                                                @Nullable final VariableMapping newMapping,
                                                @Nullable final VariableMapping oldMapping) {
        super(shaderNode, newMapping, oldMapping);
    }

    @Override
    @JmeThread
    protected void redoImplInJmeThread(@NotNull final ShaderNodesChangeConsumer editor) {
        super.redoImplInJmeThread(editor);

        final List<VariableMapping> inputMapping = getShaderNode().getInputMapping();

        if (getOldMapping() != null) {
            inputMapping.remove(getOldMapping());
        }

        if (getNewMapping() != null) {
            inputMapping.add(getNewMapping());
        }
    }

    @Override
    @JmeThread
    protected void undoImplInJmeThread(@NotNull final ShaderNodesChangeConsumer editor) {
        super.undoImplInJmeThread(editor);

        final List<VariableMapping> inputMapping = getShaderNode().getInputMapping();

        if (getNewMapping() != null) {
            inputMapping.remove(getNewMapping());
        }

        if (getOldMapping() != null) {
            inputMapping.add(getOldMapping());
        }
    }
}
