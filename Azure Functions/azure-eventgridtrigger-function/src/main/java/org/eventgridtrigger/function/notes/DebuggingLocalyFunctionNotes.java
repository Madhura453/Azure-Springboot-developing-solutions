package org.eventgridtrigger.function.notes;

public class DebuggingLocalyFunctionNotes {
    /*
    For debugging function locally install ngrok
    configure ngrok http  55157
     then the url will be
     https://31bf-2401-4900-60fa-f038-34f4-7e10-a426-5bf0.ap.ngrok.io/runtime/webhooks/EventGrid?functionName=EventGridTriggerFunction

     in stroage account in endpoint type select as webhook
     in endpoint give this url

    the local url is http://localhost:55157/runtime/webhooks/EventGrid?functionName=EventGridTriggerFunction
     To test this url follow this ariticle

     header is aeg-event-type=Notification

     The body is Notes class body


     https://www.c-sharpcorner.com/article/how-to-debug-azure-event-grid-trigger-function-using-postman/
     */
}
