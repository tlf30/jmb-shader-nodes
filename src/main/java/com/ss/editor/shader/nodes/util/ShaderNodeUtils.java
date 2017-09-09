package com.ss.editor.shader.nodes.util;

import com.jme3.scene.VertexBuffer;
import com.jme3.shader.ShaderNode;
import com.jme3.shader.ShaderNodeVariable;
import com.jme3.shader.VariableMapping;
import com.ss.editor.shader.nodes.editor.shader.node.ShaderNodeElement;
import com.ss.editor.shader.nodes.editor.shader.node.global.OutputGlobalShaderNodeElement;
import com.ss.editor.shader.nodes.editor.shader.node.parameter.InputShaderNodeParameter;
import com.ss.editor.shader.nodes.editor.shader.node.parameter.OutputShaderNodeParameter;
import com.ss.rlib.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * The utility class.
 *
 * @author JavaSaBr
 */
public class ShaderNodeUtils {

    /**
     * Compare two variables by name and namespace.
     *
     * @param first  the first variable.
     * @param second the second variable.
     * @return true of the variables are equal.
     */
    public static boolean equalsByNameAndNameSpace(@NotNull final ShaderNodeVariable first,
                                                   @NotNull final ShaderNodeVariable second) {
        return StringUtils.equals(first.getName(), second.getName()) &&
                StringUtils.equals(first.getNameSpace(), second.getNameSpace());
    }

    /**
     * Compare two variables by name.
     *
     * @param first  the first variable.
     * @param second the second variable.
     * @return true of the variables are equal.
     */
    public static boolean equalsByName(@NotNull final ShaderNodeVariable first,
                                       @NotNull final ShaderNodeVariable second) {
        return StringUtils.equals(first.getName(), second.getName());
    }

    /**
     * Find an output mapping with the left variable by name and namespace.
     *
     * @param shaderNode the shader node.
     * @param variable   the left variable.
     * @return the mapping or null.
     */
    public static @Nullable VariableMapping findOutMappingByNNLeftVar(@NotNull final ShaderNode shaderNode,
                                                                      @NotNull final ShaderNodeVariable variable) {
        return shaderNode.getOutputMapping().stream()
                .filter(mapping -> equalsByNameAndNameSpace(mapping.getLeftVariable(), variable))
                .findAny().orElse(null);
    }

    /**
     * Find an input mapping with the left variable by name.
     *
     * @param shaderNode the shader node.
     * @param variable   the left variable.
     * @return the mapping or null.
     */
    public static @Nullable VariableMapping findInMappingByNLeftVar(@NotNull final ShaderNode shaderNode,
                                                                    @NotNull final ShaderNodeVariable variable) {
        return shaderNode.getInputMapping().stream()
                .filter(mapping -> equalsByName(mapping.getLeftVariable(), variable))
                .findAny().orElse(null);
    }

    /**
     * Find an input mapping with the left variable by name and namespace.
     *
     * @param shaderNode the shader node.
     * @param variable   the left variable.
     * @return the mapping or null.
     */
    public static @Nullable VariableMapping findInMappingByNNLeftVar(@NotNull final ShaderNode shaderNode,
                                                                    @NotNull final ShaderNodeVariable variable) {
        return shaderNode.getInputMapping().stream()
                .filter(mapping -> equalsByNameAndNameSpace(mapping.getLeftVariable(), variable))
                .findAny().orElse(null);
    }

    /**
     * Check the shader node.
     *
     * @param shaderNode the shader node.
     * @param variable   the left variable.
     * @return true if the shader node has the output mapping with the left variable.
     */
    public static boolean hasOutMappingByLeftVar(@NotNull final ShaderNode shaderNode,
                                                 @NotNull final ShaderNodeVariable variable) {
        return shaderNode.getOutputMapping()
                .stream().anyMatch(mapping -> equalsByNameAndNameSpace(mapping.getLeftVariable(), variable));
    }

    /**
     * Check the shader node.
     *
     * @param shaderNode the shader node.
     * @param variable   the right variable.
     * @return true if the shader node has the input mapping with the right variable.
     */
    public static boolean hasInMappingByRightVar(@NotNull final ShaderNode shaderNode,
                                                 @NotNull final ShaderNodeVariable variable) {
        return shaderNode.getInputMapping()
                .stream().anyMatch(mapping -> equalsByNameAndNameSpace(mapping.getRightVariable(), variable));
    }

    /**
     * Make a new mapping between the parameters.
     *
     * @param inputParameter  the input parameter.
     * @param outputParameter the output parameter.
     * @return the new mapping.
     */
    public static @NotNull VariableMapping makeMapping(@NotNull final InputShaderNodeParameter inputParameter,
                                                       @NotNull final OutputShaderNodeParameter outputParameter) {

        final ShaderNodeElement<?> inElement = inputParameter.getNodeElement();
        final Object inObject = inElement.getObject();

        final ShaderNodeElement<?> outElement = outputParameter.getNodeElement();
        final Object outObject = outElement.getObject();

        final ShaderNodeVariable inVar = inputParameter.getVariable();
        final ShaderNodeVariable outVar = outputParameter.getVariable();

        final String inNameSpace;

        if (inObject instanceof ShaderNode) {
            inNameSpace = ((ShaderNode) inObject).getDefinition().getName();
        } else {
            inNameSpace = inVar.getNameSpace();
        }

        final String outNameSpace;

        if (outObject instanceof ShaderNode) {
            outNameSpace = ((ShaderNode) outObject).getDefinition().getName();
        } else {
            outNameSpace = outVar.getNameSpace();
        }

        final boolean isShaderOutput = inElement instanceof OutputGlobalShaderNodeElement;

        final VariableMapping newMapping = new VariableMapping();
        newMapping.setLeftVariable(new ShaderNodeVariable(inVar.getType(), inNameSpace, inVar.getName(),
                null, inVar.getPrefix()));
        newMapping.setRightVariable(new ShaderNodeVariable(outVar.getType(), outNameSpace, outVar.getName(),
                null, outVar.getPrefix()));

        newMapping.getLeftVariable().setShaderOutput(isShaderOutput);

        return newMapping;
    }

    /**
     * Get the UI type of the attribute.
     *
     * @param attribute the attribute.
     * @return the UI type.
     */
    public static @NotNull String getAttributeUIType(@NotNull final VertexBuffer.Type attribute) {
        switch (attribute) {
            case BoneWeight:
            case BindPoseNormal:
            case Binormal:
            case Normal: {
                return "Vector 3 x Float";
            }
            case Size: {
                return "Float";
            }
            case Position:
            case BindPosePosition:
            case BindPoseTangent:
            case Tangent: {
                return "Vector 4 x Float";
            }
            case Color: {
                return "Color";
            }
            case InterleavedData: {
                return "Integer";
            }
            case Index: {
                return "Unsigned Integer";
            }
            case BoneIndex: {
                return "Vector 4 x Unsigned Integer";
            }
            case TexCoord:
            case TexCoord2:
            case TexCoord3:
            case TexCoord4:
            case TexCoord5:
            case TexCoord6:
            case TexCoord7:
            case TexCoord8: {
                return "Vector 2 x Float";
            }
        }
        throw new RuntimeException("unknown attribute " + attribute);
    }

    /**
     * Convert UI type of shader to real type.
     *
     * @param type the UI type.
     * @return the real type.
     */
    public static @NotNull String uiTypeToType(@NotNull final String type) {
        switch (type) {
            case "Vector 4 x Unsigned Integer":
                return "uvec4";
            case "Color":
                return "vec4";
            case "Vector 4 x Float":
                return "vec4";
            case "Vector 3 x Float":
                return "vec3";
            case "Vector 2 x Float":
                return "vec2";
            case "Unsigned Integer":
                return "uint";
            case "Integer":
                return "int";
            case "Float":
                return "float";
            default:
                return type;
        }
    }

    /**
     * Check the list of variables to contain a variable with the name and the namespace.
     *
     * @param variables the variables.
     * @param name      the name.
     * @param nameSpace the namespace.
     * @return true of the list contains it.
     */
    public static boolean containsByNN(@NotNull final Collection<ShaderNodeVariable> variables,
                                       @NotNull final String name, @NotNull final String nameSpace) {

        for (final ShaderNodeVariable variable : variables) {

            if (!StringUtils.equals(variable.getNameSpace(), nameSpace)) {
                continue;
            } else if (!StringUtils.equals(variable.getName(), name)) {
                continue;
            }

            return true;
        }

        return false;
    }
}
