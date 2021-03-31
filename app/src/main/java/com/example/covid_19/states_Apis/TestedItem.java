package com.example.covid_19.states_Apis;

/**
 * Created by JacksonGenerator on 3/4/20.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TestedItem
{
    @JsonProperty("aefi")
    String aefi;

    @JsonProperty("dailyrtpcrsamplescollectedicmrapplication")
    String dailyrtpcrsamplescollectedicmrapplication;

    @JsonProperty("firstdoseadministered")
    String firstdoseadministered;

    @JsonProperty("frontlineworkersvaccinated1stdose")
    String frontlineworkersvaccinated1stdose;

    @JsonProperty("healthcareworkersvaccinated1stdose")
    String healthcareworkersvaccinated1stdose;

    @JsonProperty("frontlineworkersvaccinated2nddose")
    String healthcareworkersvaccinated2nddose;

    @JsonProperty("over60years1stdose")
    String over60years1stdose;

    @JsonProperty("over60years2nddose")
    String over60years2nddose;

    @JsonProperty("positivecasesfromsamplesreported")
    String positivecasesfromsamplesreported;

    @JsonProperty("registrationflwhcw")
    String registrationflwhcw;

    @JsonProperty("registrationonline")
    String registrationonline;

    @JsonProperty("registrationonspot")
    String registrationonspot;

    @JsonProperty("samplereportedtoday")
    String samplereportedtoday;

    @JsonProperty("seconddoseadministered")
    String seconddoseadministered;

    @JsonProperty("source")
    String source;

    @JsonProperty("source2")
    String source2;

    @JsonProperty("source3")
    String source3;

    @JsonProperty("source4")
    String source4;


    @JsonProperty("testedasof")
    String testedasof;

    @JsonProperty("to60yearswithco-morbidities1stdose")
    String to60yearswithcomorbidities1stdose;

    @JsonProperty("to60yearswithco-morbidities2nddose")
    String to60yearswithcomorbidities2nddose;

    @JsonProperty("totaldosesadministered")
    String totaldosesadministered;

    @JsonProperty("totalindividualsvaccinated")
    String totalindividualsvaccinated;

    @JsonProperty("totalrtpcrsamplescollectedicmrapplication")
    String totalrtpcrsamplescollectedicmrapplication;

    @JsonProperty("totalsamplestested")
    String totalsamplestested;

    @JsonProperty("totalsessionsconducted")
    String totalsessionsconducted;

    @JsonProperty("updatetimestamp")
    String updatetimestamp;

    public String getAefi() {
        return aefi;
    }

    public String getDailyrtpcrsamplescollectedicmrapplication() {
        return dailyrtpcrsamplescollectedicmrapplication;
    }

    public String getFirstdoseadministered() {
        return firstdoseadministered;
    }

    public String getFrontlineworkersvaccinated1stdose() {
        return frontlineworkersvaccinated1stdose;
    }

    public String getHealthcareworkersvaccinated1stdose() {
        return healthcareworkersvaccinated1stdose;
    }

    public String getHealthcareworkersvaccinated2nddose() {
        return healthcareworkersvaccinated2nddose;
    }

    public String getOver60years1stdose() {
        return over60years1stdose;
    }

    public String getOver60years2nddose() {
        return over60years2nddose;
    }

    public String getPositivecasesfromsamplesreported() {
        return positivecasesfromsamplesreported;
    }

    public String getRegistrationflwhcw() {
        return registrationflwhcw;
    }

    public String getRegistrationonline() {
        return registrationonline;
    }

    public String getRegistrationonspot() {
        return registrationonspot;
    }

    public String getSamplereportedtoday() {
        return samplereportedtoday;
    }

    public String getSeconddoseadministered() {
        return seconddoseadministered;
    }

    public String getSource() {
        return source;
    }

    public String getSource2() {
        return source2;
    }

    public String getSource3() {
        return source3;
    }

    public String getSource4() {
        return source4;
    }

    public String getTestedasof() {
        return testedasof;
    }

    public String getTo60yearswithcomorbidities1stdose() {
        return to60yearswithcomorbidities1stdose;
    }

    public String getTo60yearswithcomorbidities2nddose() {
        return to60yearswithcomorbidities2nddose;
    }

    public String getTotaldosesadministered() {
        return totaldosesadministered;
    }

    public String getTotalindividualsvaccinated() {
        return totalindividualsvaccinated;
    }

    public String getTotalrtpcrsamplescollectedicmrapplication() {
        return totalrtpcrsamplescollectedicmrapplication;
    }

    public String getTotalsamplestested() {
        return totalsamplestested;
    }

    public String getTotalsessionsconducted() {
        return totalsessionsconducted;
    }

    public String getUpdatetimestamp() {
        return updatetimestamp;
    }
}