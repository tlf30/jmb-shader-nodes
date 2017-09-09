package com.ss.editor.shader.nodes.editor.shader.node.action;

import static com.ss.editor.extension.property.EditablePropertyType.STRING_FROM_LIST;
import com.jme3.material.TechniqueDef;
import com.jme3.math.Vector2f;
import com.ss.editor.annotation.FXThread;
import com.ss.editor.plugin.api.dialog.GenericFactoryDialog;
import com.ss.editor.plugin.api.property.PropertyDefinition;
import com.ss.editor.shader.nodes.editor.shader.ShaderNodesContainer;
import com.ss.rlib.util.VarTable;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The base implementation of an action to create a constant parameter.
 *
 * @author JavaSaBr
 */
public abstract class AddTechniqueDefParameterShaderModeAction extends ShaderNodeAction<TechniqueDef> {

    @NotNull
    protected static final String PROP_NAME = "name";

    /**
     * The list of available attributes.
     */
    @NotNull
    private final Array<String> available;

    public AddTechniqueDefParameterShaderModeAction(@NotNull final ShaderNodesContainer container,
                                                    @NotNull final TechniqueDef techniqueDef,
                                                    @NotNull final Vector2f location) {
        super(container, techniqueDef, location);
        this.available = ArrayFactory.newArray(String.class);
    }

    /**
     * @return the list of available attributes.
     */
    protected @NotNull Array<String> getAvailable() {
        return available;
    }

    @Override
    @FXThread
    protected void process() {
        super.process();

        final Array<PropertyDefinition> definitions = ArrayFactory.newArray(PropertyDefinition.class);
        definitions.add(new PropertyDefinition(STRING_FROM_LIST, "name", PROP_NAME, available.first(), available));

        final GenericFactoryDialog dialog = new GenericFactoryDialog(definitions, this::addParameter, this::validate);
        dialog.show();
    }

    /**
     * Validate inputs from the dialog.
     *
     * @param vars the variables.
     * @return true if the values are valid.
     */
    @FXThread
    protected boolean validate(@NotNull final VarTable vars) {
        return available.contains(vars.getString(PROP_NAME));
    }

    /**
     * Add the new material parameter.
     *
     * @param vars the variables.
     */
    @FXThread
    protected abstract void addParameter(@NotNull final VarTable vars);
}