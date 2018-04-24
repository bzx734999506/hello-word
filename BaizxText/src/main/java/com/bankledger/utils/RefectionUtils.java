package com.bankledger.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class RefectionUtils {
	
	public static void writeDeclaredField(Object target, String fieldName, Object value) 
	          throws IllegalAccessException {
	      if (target == null) {
	          throw new IllegalArgumentException("target object must not be null");
	      }
	      Class<?> cls = target.getClass();
	      Field field = getField(cls, fieldName);
	      if (field == null) {
	          throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
	      }
	      field.set(target, value);
	 }

	 public static Object readField(Object target, String fieldName)
	          throws IllegalAccessException {
	      if (target == null) {
	          throw new IllegalArgumentException("target object must not be null");
	      }
	      Class<?> cls = target.getClass();
	      Field field = getField(cls, fieldName);
	      if (field == null) {
	          throw new IllegalArgumentException("Cannot locate field " + fieldName + " on " + cls);
	      }
	      if (!field.isAccessible()) {
	          field.setAccessible(true);
	      }
	      return field.get(target);
	 }

	 public static Field getField(final Class<?> cls, String fieldName) {
	      for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
	          try {
	              Field field = acls.getDeclaredField(fieldName);
	              if (!Modifier.isPublic(field.getModifiers())) {
	                  field.setAccessible(true);
	                  return field;
	              }
	          } catch (NoSuchFieldException ex) {
	             
	          }
	      }
	         return null;
	 }


	public static void main(String[] args) {
		
	}

}
