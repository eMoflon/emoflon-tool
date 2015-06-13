package org.moflon.moca.inject;

/**
 * @deprecated Do not use this anymore
 */
@Deprecated //TODO: remove
public enum InjectionErrorHandlingMode {
   /**
    * In this mode, the first occurring exception causes an abortion of the injection process.
    */
   THROW_IMMEDIATELY,
   /**
    * In this mode, all errors are collectively printed, but the process is not aborted.
    */
   REPORT_ERROR_SUMMARY;
}