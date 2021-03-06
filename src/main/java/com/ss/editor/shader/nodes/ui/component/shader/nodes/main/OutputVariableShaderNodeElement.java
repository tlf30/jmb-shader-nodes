package com.ss.editor.shader.nodes.ui.component.shader.nodes.main;

import com.jme3.shader.ShaderNodeVariable;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.shader.nodes.ui.component.shader.nodes.ShaderNodesContainer;
import com.ss.editor.shader.nodes.ui.component.shader.nodes.parameter.OutputShaderNodeParameter;
import com.ss.rlib.ui.util.FXUtils;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of nodes element to present output shader variable.
 *
 * @author JavaSaBr
 */
public class OutputVariableShaderNodeElement extends VariableShaderNodeElement {

    public OutputVariableShaderNodeElement(@NotNull final ShaderNodesContainer container,
                                           @NotNull final ShaderNodeVariable variable) {
        super(container, variable);
    }

    @Override
    @FXThread
    protected void fillParameters(@NotNull final VBox container) {
        super.fillParameters(container);
        FXUtils.addToPane(newParameter(), container);
    }

    /**
     * Create a new output parameter.
     *
     * @return the output parameter.
     */
    @FXThread
    protected @NotNull OutputShaderNodeParameter newParameter() {
        return new OutputShaderNodeParameter(this, getObject());
    }
}
