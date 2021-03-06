package com.ss.editor.shader.nodes.ui.component.shader.nodes.action.remove;

import com.jme3.material.MatParam;
import com.jme3.material.MaterialDef;
import com.jme3.material.TechniqueDef;
import com.jme3.math.Vector2f;
import com.jme3.shader.ShaderNode;
import com.jme3.shader.ShaderNodeVariable;
import com.ss.editor.Messages;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.shader.nodes.ui.component.shader.nodes.ShaderNodesContainer;
import com.ss.editor.shader.nodes.ui.component.shader.nodes.action.ShaderNodeAction;
import com.ss.editor.shader.nodes.ui.component.shader.nodes.main.MainShaderNodeElement;
import com.ss.editor.shader.nodes.ui.component.shader.nodes.operation.remove.RemoveMaterialParameterVariableOperation;
import com.ss.editor.shader.nodes.ui.component.editor.ShaderNodesChangeConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The action to remove an old material param.
 *
 * @author JavaSaBr
 */
public class RemoveMaterialParamShaderNodeAction extends ShaderNodeAction<ShaderNodeVariable> {

    public RemoveMaterialParamShaderNodeAction(@NotNull final ShaderNodesContainer container,
                                               @NotNull final ShaderNodeVariable variable,
                                               @NotNull final Vector2f location) {
        super(container, variable, location);
    }

    @Override
    @FXThread
    protected @NotNull String getName() {
        return Messages.MODEL_NODE_TREE_ACTION_REMOVE;
    }

    @Override
    @FXThread
    protected void process() {
        super.process();

        final ShaderNodesContainer container = getContainer();
        final MaterialDef materialDef = container.getMaterialDef();
        final TechniqueDef techniqueDef = container.getTechniqueDef();
        final ShaderNodeVariable variable = getObject();
        final MatParam matParam = materialDef.getMaterialParam(variable.getName());

        final List<ShaderNode> usingNodes = container.findWithRightInputVar(variable, MainShaderNodeElement.class);

        final ShaderNodesChangeConsumer consumer = container.getChangeConsumer();
        consumer.execute(new RemoveMaterialParameterVariableOperation(usingNodes, materialDef, techniqueDef,
                matParam, variable, getLocation()));
    }
}
