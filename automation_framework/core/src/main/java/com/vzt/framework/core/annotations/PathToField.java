package com.vzt.framework.core.annotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a  path from the reaching the specific fields through a list of other fields of a encompassing classes. 
 * @author prokarma
 * @version 1.0
 */
public class PathToField {

    private final Field targetField;
    private final List<Field> path;
    
    /**
     * Build for the path to the specific field.
     * 
     */
    public static class Builder {
        
        private Field targetField;
        private List<Field> path;

        /**
         * Initialize the builder.
         */
        public Builder() {
            path = new ArrayList<Field>();
        }
        
        /**
         * Adds the parent to the field path as the first element.
         * @param parent
         */
        public void addParent(Field parent) {
            path.add(parent);
        }
        
        /**
         * Sets the target field.
         * @param field
         */
        public void setTarget(Field targetField) {
            this.targetField = targetField;
        }
        
        /**
         * Build and return a instance of the PathToField object.
         * @return
         */
        public PathToField build() {
            return new PathToField(targetField, copyPath());
        }
        
        /**
         * Reset the builder instances and clears the target feild.
         */
        public Builder reset() {
        	targetField = null;
        	return this;
        }

		private List<Field> copyPath() {
			List<Field> newPath = new ArrayList<Field>(path.size());
        	for(Field field : path) {
        		newPath.add(field);
        	}
        	return newPath;
		}
    }

    /**
     * @param targetField
     * @param path
     */
    private PathToField(Field targetField, List<Field> path) {
        this.targetField = targetField;
        this.path = path;
    }

    /**
     * @return the targetField
     */
    public Field getTargetField() {
        return targetField;
    }

    /**
     * @return the path
     */
    public List<Field> getPath() {
        return path;
    }
    
    /**
     * Returns true if the field is part of the top level parent.
     * @return
     */
    public boolean isPartOfTopParent() {
        return path.isEmpty();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PathToField [" + (targetField != null ? "targetField=" + targetField + ", " : "")
                + (path != null ? "path=" + path : "") + "]";
    }
}
