package org.msf.records.net;

import com.android.volley.Response;

import org.msf.records.model.CustomSerialization;
import org.msf.records.model.PatientChart;

import java.util.HashMap;

/**
 * A connection to an Open MRS backend to get chart information. This is observations on encounters
 * with patients. The design for the resources is here:
 * https://docs.google.com/document/d/17Dub0KZDEahIqJgNOW6kH789K0Brwj9leapSMY1JC-k/edit
 * <p>
 * There are essentially three endpoints. /patientencounters which give encoded details of
 * the observations of concept values that happen at an encounter, /concepts which gives localised
 * string and type information for the concepts observed, and /charts which give display information
 * about how to display those encounters, so you can have consistent ordering of observations and
 * grouping into sections.
 */
public class OpenMrsChartServer {

    private static final String TAG = "OpenMrsChartServer";
    private final OpenMrsConnectionDetails mConnectionDetails;

    public OpenMrsChartServer(OpenMrsConnectionDetails connectionDetails) {
        this.mConnectionDetails = connectionDetails;
    }

    public void getChart(String patientUuid,
                         Response.Listener<PatientChart> patientListener,
                         Response.ErrorListener errorListener) {
        GsonRequest<PatientChart> request = new GsonRequest<>(
                mConnectionDetails.rootUrl + "/patientencounters/" + patientUuid,
                PatientChart.class, false,
                mConnectionDetails.addAuthHeader(new HashMap<String, String>()),
                patientListener, errorListener);
        CustomSerialization.registerTo(request.getGson());
        mConnectionDetails.volley.addToRequestQueue(request, TAG);
    }
}