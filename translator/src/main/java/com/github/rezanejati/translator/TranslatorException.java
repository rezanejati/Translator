package com.github.rezanejati.translator;

/**
 * Created by Reza.nejati on 3/25/2018.
 */


public class TranslatorException extends RuntimeException {

   public TranslatorException() {
       super();
   }


   public TranslatorException(String message) {
       super(message);
   }


   public TranslatorException(String message, Throwable cause) {
       super(message, cause);
   }

   public TranslatorException(Throwable cause) {
       super(cause);
   }
}
