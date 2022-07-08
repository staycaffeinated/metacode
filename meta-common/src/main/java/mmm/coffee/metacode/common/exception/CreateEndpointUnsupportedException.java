/**
 * Copyright (c) 2022, Jon Caulfield. All rights reserved.
 */
package mmm.coffee.metacode.common.exception;


import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * When the user attempts the `create endpoint` command within
 * a project template that does not support that, this exception is thrown.
 *
 * An ExceptionHandler is plugged into the picocli CommandLine instance
 * to capture instances of this exception and print an error message.
 */ 
@Generated
public class CreateEndpointUnsupportedException extends RuntimeException {
  public CreateEndpointUnsupportedException() {}
  
  public CreateEndpointUnsupportedException(final String msg) {
    super(msg);
  }

  public CreateEndpointUnsupportedException(final String msg, Throwable cause) {
    super(msg, cause);
  }

  public CreateEndpointUnsupportedException(Throwable cause) {
    super(cause);
  }
  
}