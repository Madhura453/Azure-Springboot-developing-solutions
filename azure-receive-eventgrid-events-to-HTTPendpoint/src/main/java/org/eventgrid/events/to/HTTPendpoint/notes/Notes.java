package org.eventgrid.events.to.HTTPendpoint.notes;

public class Notes {
    /*
    https://learn.microsoft.com/en-us/java/api/com.microsoft.azure.eventgrid.customization.eventgridsubscriber?view=azure-java-stables

    The below follow URls to

   https://e4ec-2401-4900-60eb-40c6-ecac-5142-a1a4-3b08.ap.ngrok.io/api/HttpExample  = ngrok url

     http://localhost:51496/api/HttpExample =local url

     This is service ment for receiving event-grid events from anything like blob storage, custom event grid topic.

     This is HTTP endpoint we need to send validation code back to the event-grid subscription while creating it.
     In creating event-grid subscription it will do handshake b/w your URL ngrok url and event-grid subscription. If you provide back to
      validation code. The deployment will succeed.
     */
}
