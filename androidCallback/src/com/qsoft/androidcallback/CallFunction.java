package com.qsoft.androidcallback;

import java.lang.reflect.InvocationTargetException;

import android.os.Handler;
import android.util.Log;

/**
 * @author quynhlt
 * 
 *         HOW TO USE
 * 
 *         Allow call a method (contains parameter or not)from class with
 *         specific time delay
 * 
 *         // myFunction must declare the public method
 * 
 *         //Call myFunction with time delay is 0 millisecond, no parameter
 * 
 *         new CallFunction(0, "myFunction", new MyClass(), null);
 * 
 *         //Call myFunction with time delay is 2000 millisecond (2s), contains
 *         parameter is String value
 * 
 *         Object[] pamrams = {"send message from Main Activity"}; new
 *         CallFunction(2000, "myFunction", new MyClass(), pamrams);
 * 
 *         //Call myFunction with time delay is 4000 millisecond (4s), contains
 *         parameter is Object value
 * 
 *         MyEntity myEntity = new MyEntity();
 *         myEntity.setMessage("Message of Entity from main activity");
 * 
 *         Object[] pamramsObj = {myEntity};
 * 
 *         new CallFunction(4000, "myFunction", new MyClass(), pamramsObj);
 * 
 * 
 *         *******************************************************
 * 
 *         public class MyClass{
 * 
 *         public void myFunction(){
 * 
 *         Log.e("myFunction", "No parameter");
 * 
 *         }
 * 
 *         public void myFunction(String message){
 * 
 *         Log.e("myFunction", message);
 * 
 *         }
 * 
 *         public void myFunction(MyEntity entity){
 * 
 *         Log.e("New message of entity", entity.getMessage()); }
 * 
 *         }
 * 
 *         ****************************************************************
 * 
 *         public class MyEntity {
 * 
 *         private String message;
 * 
 *         public String getMessage() { return message; }
 * 
 *         public void setMessage(String message) { this.message = message; }
 * 
 *         }
 * 
 */
public class CallFunction {
	private String tag = "[CallFunction]";

	/**
	 * Call a function after delay contains parameters.
	 * 
	 * @param delay
	 *            The delay (in milliseconds) until the Runnable will be
	 *            executed.
	 * @param methodName
	 *            The name of the method to call.
	 * @param className
	 *            Class contains method to call.
	 * @param params
	 *            The parameter of the method to call.
	 */
	public CallFunction(final String methodName, final Object className,
			final int delay, final Object[] params) {
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				new CallFunction(methodName, className, params);
			}
		};
		handler.postDelayed(runnable, delay);
	}

	/**
	 * Call a function after delay contains parameters.
	 * 
	 * @param delay
	 *            The delay (in milliseconds) until the Runnable will be
	 *            executed.
	 * @param methodName
	 *            The name of the method to call.
	 * @param className
	 *            Class contains method to call.
	 * @param params
	 *            The parameter of the method to call.
	 */
	public CallFunction(final String methodName, final Object className,
			final Object[] params) {
		CallBack callBack;
		try {
			callBack = new CallBack(className, methodName);
			if (params == null) {
				callBack.invoke();
			} else {
				callBack.invoke(params);
			}
			Log.d(tag, "Run " + methodName + "() function of " + className);
		} catch (InvocationTargetException e) {
			Log.d(tag, e.getMessage());
		} catch (IllegalAccessException e) {
			Log.d(tag, e.getMessage());
		} catch (NoSuchMethodException e) {
			Log.d(tag, e.getMessage());
		} catch (Exception e) {
			Log.d(tag, e.getMessage());
		}
	}

}
