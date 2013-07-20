package com.qsoft.androidcallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author quynhlt
 * 
 */
public class CallBack {
	private String methodName;
	private Object scope;

	/**
	 * @param scope
	 *            Class contains method to call.
	 * @param methodName
	 *            The name of the method to call.
	 */
	public CallBack(Object scope, String methodName) {
		this.methodName = methodName;
		this.scope = scope;
	}

	/**
	 * @param parameters
	 *            The parameter of the method to call.
	 * @throws InvocationTargetException
	 *             if an exception was thrown by the invoked method
	 * 
	 * @throws IllegalAccessException
	 *             if this method is not accessible (see AccessibleObject)
	 *             IllegalArgumentException if the number of arguments doesn't
	 *             match the number of parameters, the receiver is incompatible
	 *             with the declaring class, or an argument could not be unboxed
	 *             or converted by a widening conversion to the corresponding
	 *             parameter type
	 * 
	 * @throws NoSuchMethodException
	 *             Thrown when the VM notices that a program tries to reference,
	 *             on a class or object, a method that does not exist.
	 * 
	 * @return Returns the result of dynamically invoking this method.
	 */
	public Object invoke(Object... parameters) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		Method method = scope.getClass().getMethod(methodName, getParameterClasses(parameters));
		return method.invoke(scope, parameters);
	}

	/**
	 * @param parameters
	 *            parameters to get class.
	 * @return Class[]
	 * 
	 *         Returns the unique instance of Class that represents this
	 *         object's class. Note that getClass() is a special case in that it
	 *         actually returns Class where Foo is the erasure of the type of
	 *         the expression getClass() was called upon.
	 */
	@SuppressWarnings("rawtypes")
	private Class[] getParameterClasses(Object... parameters) {
		Class[] classes = new Class[parameters.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = parameters[i].getClass();
		}
		return classes;
	}
}
