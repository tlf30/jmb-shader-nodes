package com.ss.editor.shader.nodes.ui.component.shader.nodes.operation.attach;

import com.jme3.shader.ShaderNode;
import com.jme3.shader.VariableMapping;
import com.ss.editor.annotation.JMEThread;
import com.ss.editor.shader.nodes.ui.component.editor.ShaderNodesChangeConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The implementation of attaching an uniform parameter to a shader nodes.
 *
 * @author JavaSaBr
 */
public class AttachGlobalToShaderNodeOperation extends AttachShaderNodeOperation {

    public AttachGlobalToShaderNodeOperation(@NotNull final ShaderNode shaderNode,
                                             @Nullable final VariableMapping newMapping,
                                             @Nullable final VariableMapping oldMapping) {
        super(shaderNode, newMapping, oldMapping);
    }

    @Override
    @JMEThread
    protected void redoImplInJMEThread(@NotNull final ShaderNodesChangeConsumer editor) {
        super.redoImplInJMEThread(editor);

        final List<VariableMapping> inputMapping = getShaderNode().getInputMapping();

        if (getOldMapping() != null) {
            inputMapping.remove(getOldMapping());
        }

        if (getNewMapping() != null) {
            inputMapping.add(getNewMapping());
        }
    }

    @Override
    @JMEThread
    protected void undoImplInJMEThread(@NotNull final ShaderNodesChangeConsumer editor) {
        super.undoImplInJMEThread(editor);

        final List<VariableMapping> inputMapping = getShaderNode().getInputMapping();

        if (getNewMapping() != null) {
            inputMapping.remove(getNewMapping());
        }

        if (getOldMapping() != null) {
            inputMapping.add(getOldMapping());
        }
    }
}
