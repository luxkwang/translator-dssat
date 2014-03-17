package org.agmip.translators.dssat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.agmip.translators.dssat.DssatCommonInput.copyItem;
import static org.agmip.util.MapUtil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DSSAT Experiment Data I/O API Class
 *
 * @author Meng Zhang
 * @version 1.0
 */
public class DssatXFileOutput extends DssatCommonOutput {

    private static final Logger LOG = LoggerFactory.getLogger(DssatXFileOutput.class);
//    public static final DssatCRIDHelper crHelper = new DssatCRIDHelper();

    /**
     * DSSAT Experiment Data Output method
     *
     * @param arg0 file output path
     * @param result data holder object
     */
    @Override
    public void writeFile(String arg0, Map result) {

        // Initial variables
        HashMap expData = (HashMap) result;
        ArrayList<HashMap> soilArr = readSWData(expData, "soil");
        ArrayList<HashMap> wthArr = readSWData(expData, "weather");
        HashMap soilData;
        HashMap wthData;
        BufferedWriter bwX;                          // output object
        StringBuilder sbGenData = new StringBuilder();      // construct the data info in the output
        StringBuilder sbDomeData = new StringBuilder();     // construct the dome info in the output
        StringBuilder sbNotesData = new StringBuilder();      // construct the data info in the output
        StringBuilder sbData = new StringBuilder();         // construct the data info in the output
        StringBuilder eventPart2 = new StringBuilder();                   // output string for second part of event data
        HashMap sqData;
        ArrayList<HashMap> evtArr;            // Arraylist for section data holder
        HashMap evtData;
//        int trmnNum;                            // total numbers of treatment in the data holder
        int cuNum;                              // total numbers of cultivars in the data holder
        int flNum;                              // total numbers of fields in the data holder
        int saNum;                              // total numbers of soil analysis in the data holder
        int icNum;                              // total numbers of initial conditions in the data holder
        int mpNum;                              // total numbers of plaintings in the data holder
        int miNum;                              // total numbers of irrigations in the data holder
        int mfNum;                              // total numbers of fertilizers in the data holder
        int mrNum;                              // total numbers of residues in the data holder
        int mcNum;                              // total numbers of chemical in the data holder
        int mtNum;                              // total numbers of tillage in the data holder
        int meNum;                              // total numbers of enveronment modification in the data holder
        int mhNum;                              // total numbers of harvest in the data holder
        int smNum;                              // total numbers of simulation controll record
        ArrayList<HashMap> sqArr;     // array for treatment record
        ArrayList<HashMap> cuArr = new ArrayList();   // array for cultivars record
        ArrayList<HashMap> flArr = new ArrayList();   // array for fields record
        ArrayList<HashMap> saArr = new ArrayList();   // array for soil analysis record
        ArrayList<HashMap> icArr = new ArrayList();   // array for initial conditions record
        ArrayList<HashMap> mpArr = new ArrayList();   // array for plaintings record
        ArrayList<ArrayList<HashMap>> miArr = new ArrayList();   // array for irrigations record
        ArrayList<ArrayList<HashMap>> mfArr = new ArrayList();   // array for fertilizers record
        ArrayList<ArrayList<HashMap>> mrArr = new ArrayList();   // array for residues record
        ArrayList<ArrayList<HashMap>> mcArr = new ArrayList();   // array for chemical record
        ArrayList<ArrayList<HashMap>> mtArr = new ArrayList();   // array for tillage record
        ArrayList<ArrayList<HashMap>> meArr = new ArrayList();     // array for enveronment modification record
        ArrayList<ArrayList<HashMap>> mhArr = new ArrayList();   // array for harvest record
        ArrayList<HashMap> smArr = new ArrayList();     // array for simulation control record
//        String exName;
        boolean isFallow = false;

        try {

            // Set default value for missing data
            if (expData == null || expData.isEmpty()) {
                return;
            }
//            decompressData((HashMap) result);
            setDefVal();

            // Initial BufferedWriter
            String fileName = getFileName(result, "X");
            arg0 = revisePath(arg0);
            outputFile = new File(arg0 + fileName);
            bwX = new BufferedWriter(new FileWriter(outputFile));

            // Output XFile
            // EXP.DETAILS Section
            sbGenData.append(String.format("*EXP.DETAILS: %1$-10s %2$s\r\n\r\n",
                    getFileName(result, "").replaceAll("\\.", ""),
                    getObjectOr(expData, "local_name", defValBlank).toString()));

            // GENERAL Section
            sbGenData.append("*GENERAL\r\n");
            // People
            if (!getObjectOr(expData, "person_notes", "").equals("")) {
                sbGenData.append(String.format("@PEOPLE\r\n %1$s\r\n", getObjectOr(expData, "person_notes", defValBlank).toString()));
            }
            // Address
            if (getObjectOr(expData, "institution", "").equals("")) {
//                if (!getObjectOr(expData, "fl_loc_1", "").equals("")
//                        && getObjectOr(expData, "fl_loc_2", "").equals("")
//                        && getObjectOr(expData, "fl_loc_3", "").equals("")) {
//                    sbGenData.append(String.format("@ADDRESS\r\n %3$s, %2$s, %1$s\r\n",
//                            getObjectOr(expData, "fl_loc_1", defValBlank).toString(),
//                            getObjectOr(expData, "fl_loc_2", defValBlank).toString(),
//                            getObjectOr(expData, "fl_loc_3", defValBlank).toString()));
//                }
            } else {
                sbGenData.append(String.format("@ADDRESS\r\n %1$s\r\n", getObjectOr(expData, "institution", defValBlank).toString()));
            }

            // Site
            if (!getObjectOr(expData, "site_name", "").equals("")) {
                sbGenData.append(String.format("@SITE\r\n %1$s\r\n", getObjectOr(expData, "site_name", defValBlank).toString()));
            }
            // Plot Info
            if (isPlotInfoExist(expData)) {

                sbGenData.append("@ PAREA  PRNO  PLEN  PLDR  PLSP  PLAY HAREA  HRNO  HLEN  HARM.........\r\n");
                sbGenData.append(String.format(" %1$6s %2$5s %3$5s %4$5s %5$5s %6$-5s %7$5s %8$5s %9$5s %10$-15s\r\n",
                        formatNumStr(6, expData, "plta", defValR),
                        formatNumStr(5, expData, "pltr#", defValI),
                        formatNumStr(5, expData, "pltln", defValR),
                        formatNumStr(5, expData, "pldr", defValI),
                        formatNumStr(5, expData, "pltsp", defValI),
                        getObjectOr(expData, "pllay", defValC).toString(),
                        formatNumStr(5, expData, "pltha", defValR),
                        formatNumStr(5, expData, "plth#", defValI),
                        formatNumStr(5, expData, "plthl", defValR),
                        getObjectOr(expData, "plthm", defValC).toString()));
            }
            // Notes
            if (!getObjectOr(expData, "tr_notes", "").equals("")) {
                sbNotesData.append("@NOTES\r\n");
                String notes = getObjectOr(expData, "tr_notes", defValC).toString();
                notes = notes.replaceAll("\\\\r\\\\n", "\r\n");

                // If notes contain newline code, then write directly
                if (notes.indexOf("\r\n") >= 0) {
//                    sbData.append(String.format(" %1$s\r\n", notes));
                    sbNotesData.append(notes);
                } // Otherwise, add newline for every 75-bits charactors
                else {
                    while (notes.length() > 75) {
                        sbNotesData.append(" ").append(notes.substring(0, 75)).append("\r\n");
                        notes = notes.substring(75);
                    }
                    sbNotesData.append(" ").append(notes).append("\r\n");
                }
            }
            sbData.append("\r\n");

            // TREATMENT Section
            sqArr = getDataList(expData, "dssat_sequence", "data");
            evtArr = getDataList(expData, "management", "events");
            ArrayList<HashMap> rootArr = getObjectOr(expData, "dssat_root", new ArrayList());
            ArrayList<HashMap> meOrgArr = getDataList(expData, "dssat_environment_modification", "data");
            ArrayList<HashMap> smOrgArr = getDataList(expData, "dssat_simulation_control", "data");
            String seqId;
            String em;
            String sm;
            boolean isAnyDomeApplied = false;
            LinkedHashMap<String, String> appliedDomes = new LinkedHashMap<String, String>();
            sbData.append("*TREATMENTS                        -------------FACTOR LEVELS------------\r\n");
            sbData.append("@N R O C TNAME.................... CU FL SA IC MP MI MF MR MC MT ME MH SM\r\n");

            // if there is no sequence info, create dummy data
            if (sqArr.isEmpty()) {
                sqArr.add(new HashMap());
            }

            // Set sequence related block info
            for (int i = 0; i < sqArr.size(); i++) {
                sqData = sqArr.get(i);
                seqId = getValueOr(sqData, "seqid", defValBlank);
                em = getValueOr(sqData, "em", defValBlank);
                sm = getValueOr(sqData, "sm", defValBlank);
                if (i < soilArr.size()) {
                    soilData = soilArr.get(i);
                } else if (soilArr.isEmpty()) {
                    soilData = new HashMap();
                } else {
                    soilData = soilArr.get(0);
                }
                if (soilData == null) {
                    soilData = new HashMap();
                }
                if (i < wthArr.size()) {
                    wthData = wthArr.get(i);
                } else if (wthArr.isEmpty()) {
                    wthData = new HashMap();
                } else {
                    wthData = wthArr.get(0);
                }
                if (wthData == null) {
                    wthData = new HashMap();
                }
                HashMap cuData = new HashMap();
                HashMap flData = new HashMap();
                HashMap mpData = new HashMap();
                ArrayList<HashMap> miSubArr = new ArrayList<HashMap>();
                ArrayList<HashMap> mfSubArr = new ArrayList<HashMap>();
                ArrayList<HashMap> mrSubArr = new ArrayList<HashMap>();
                ArrayList<HashMap> mcSubArr = new ArrayList<HashMap>();
                ArrayList<HashMap> mtSubArr = new ArrayList<HashMap>();
                ArrayList<HashMap> meSubArr = new ArrayList<HashMap>();
                ArrayList<HashMap> mhSubArr = new ArrayList<HashMap>();
                HashMap smData = new HashMap();
                HashMap rootData;
                // Set exp root info
                if (i < rootArr.size()) {
                    rootData = rootArr.get(i);
                } else {
                    rootData = expData;
                }

                // Applied DOME Info
                String trt_name = getValueOr(sqData, "trt_name", getValueOr(rootData, "trt_name", getValueOr(rootData, "exname", defValC)));
                if (getValueOr(rootData, "dome_applied", "").equals("Y")) {
                    // If it comes with seasonal exname style
                    if (trt_name.matches(".+[^_]__\\d+$")) {
                        trt_name = trt_name.replaceAll("__\\d+$", "_*");
                        if (appliedDomes.get(trt_name + " Field    ") == null) {
                            appliedDomes.put(trt_name + " Field    ", getAppliedDomes(rootData, "field"));
                        }
                        if (appliedDomes.get(trt_name + " Seasonal ") == null) {
                            appliedDomes.put(trt_name + " Seasonal ", getAppliedDomes(rootData, "seasonal"));
                        }
                    } else {
                        appliedDomes.put(trt_name + " Field    ", getAppliedDomes(rootData, "field"));
                        appliedDomes.put(trt_name + " Seasonal ", getAppliedDomes(rootData, "seasonal"));
                    }
                    isAnyDomeApplied = true;
                } else {
                    appliedDomes.put(trt_name, "");
                }

                // Set field info
                copyItem(flData, rootData, "id_field");
                String dssat_wst_id = getValueOr(rootData, "dssat_wst_id", "");
                // Weather data is missing plus dssat_wst_id is available
                if (!dssat_wst_id.equals("") && wthData.isEmpty()) {
                    flData.put("wst_id", dssat_wst_id);
                } else {
                    flData.put("wst_id", getWthFileName(rootData));
                }
                copyItem(flData, rootData, "flsl");
                copyItem(flData, rootData, "flob");
                copyItem(flData, rootData, "fl_drntype");
                copyItem(flData, rootData, "fldrd");
                copyItem(flData, rootData, "fldrs");
                copyItem(flData, rootData, "flst");
                if (soilData.get("sltx") != null) {
                    copyItem(flData, soilData, "sltx");
                } else {
                    copyItem(flData, rootData, "sltx");
                }
                copyItem(flData, soilData, "sldp");
                copyItem(flData, rootData, "soil_id");
                copyItem(flData, rootData, "fl_name");
                copyItem(flData, rootData, "fl_lat");
                copyItem(flData, rootData, "fl_long");
                copyItem(flData, rootData, "flele");
                copyItem(flData, rootData, "farea");
                copyItem(flData, rootData, "fllwr");
                copyItem(flData, rootData, "flsla");
                copyItem(flData, getObjectOr(rootData, "dssat_info", new HashMap()), "flhst");
                copyItem(flData, getObjectOr(rootData, "dssat_info", new HashMap()), "fhdur");
                // remove the "_trno" in the soil_id when soil analysis is available
                String soilId = getValueOr(flData, "soil_id", "");
                if (soilId.length() > 10 && soilId.matches("\\w+_\\d+") || soilId.length() < 8) {
                    flData.put("soil_id", getSoilID(flData));
                }
                flNum = setSecDataArr(flData, flArr);

                // Set initial condition info
                icNum = setSecDataArr(getObjectOr(rootData, "initial_conditions", new HashMap()), icArr);

                // Set environment modification info
                for (int j = 0; j < meOrgArr.size(); j++) {
                    if (em.equals(meOrgArr.get(j).get("em"))) {
                        HashMap tmp = new HashMap();
                        tmp.putAll(meOrgArr.get(j));
                        tmp.remove("em");
                        meSubArr.add(tmp);
                    }
                }

                // Set soil analysis info
//                ArrayList<HashMap> icSubArr = getDataList(expData, "initial_condition", "soilLayer");
                ArrayList<HashMap> soilLarys = getObjectOr(soilData, "soilLayer", new ArrayList());
//                // If it is stored in the initial condition block
//                if (isSoilAnalysisExist(icSubArr)) {
//                    HashMap saData = new HashMap();
//                    ArrayList<HashMap> saSubArr = new ArrayList<HashMap>();
//                    HashMap saSubData;
//                    for (int i = 0; i < icSubArr.size(); i++) {
//                        saSubData = new HashMap();
//                        copyItem(saSubData, icSubArr.get(i), "sabl", "icbl", false);
//                        copyItem(saSubData, icSubArr.get(i), "sasc", "slsc", false);
//                        saSubArr.add(saSubData);
//                    }
//                    copyItem(saData, soilData, "sadat");
//                    saData.put("soilLayer", saSubArr);
//                    saNum = setSecDataArr(saData, saArr);
//                } else
                // If it is stored in the soil block
                if (isSoilAnalysisExist(soilLarys)) {
                    HashMap saData = new HashMap();
                    ArrayList<HashMap> saSubArr = new ArrayList<HashMap>();
                    HashMap saSubData;
                    for (int j = 0; j < soilLarys.size(); j++) {
                        saSubData = new HashMap();
                        copyItem(saSubData, soilLarys.get(j), "sabl", "sllb", false);
                        copyItem(saSubData, soilLarys.get(j), "saoc", "sloc", false);
                        copyItem(saSubData, soilLarys.get(j), "sasc", "slsc", false);
                        saSubArr.add(saSubData);
                    }
                    copyItem(saData, soilData, "sadat");
                    saData.put("soilLayer", saSubArr);
                    saNum = setSecDataArr(saData, saArr);
                } else {
                    saNum = 0;
                }

                // Set simulation control info
                for (int j = 0; j < smOrgArr.size(); j++) {
                    if (sm.equals(smOrgArr.get(j).get("sm"))) {
                        smData.putAll(smOrgArr.get(j));
                        smData.remove("sm");
                        break;
                    }
                }
//                if (smData.isEmpty()) {
//                    smData.put("fertilizer", mfSubArr);
//                    smData.put("irrigation", miSubArr);
//                    smData.put("planting", mpData);
//                }
                copyItem(smData, rootData, "sdat");
                copyItem(smData, getObjectOr(wthData, "weather", new HashMap()), "co2y");

                // Loop all event data
                for (int j = 0; j < evtArr.size(); j++) {
                    evtData = new HashMap();
                    evtData.putAll(evtArr.get(j));
                    // Check if it has same sequence number
                    if (getValueOr(evtData, "seqid", defValBlank).equals(seqId)) {
                        evtData.remove("seqid");

                        // Planting event
                        if (getValueOr(evtData, "event", defValBlank).equals("planting")) {
                            // Set cultivals info
                            copyItem(cuData, evtData, "cul_name");
                            copyItem(cuData, evtData, "crid");
                            copyItem(cuData, evtData, "cul_id");
                            copyItem(cuData, evtData, "dssat_cul_id");
                            copyItem(cuData, evtData, "rm");
                            copyItem(cuData, evtData, "cul_notes");
                            translateTo2BitCrid(cuData);
                            // Set planting info
                            mpData.putAll(evtData);
                            mpData.remove("cul_name");
                        } // irrigation event
                        else if (getValueOr(evtData, "event", "").equals("irrigation")) {
                            miSubArr.add(evtData);
                        } // fertilizer event
                        else if (getValueOr(evtData, "event", "").equals("fertilizer")) {
                            mfSubArr.add(evtData);
                        } // organic_matter event
                        else if (getValueOr(evtData, "event", "").equals("organic_matter")) {   // P.S. change event name to organic-materials; Back to organic_matter again.
                            mrSubArr.add(evtData);
                        } // chemical event
                        else if (getValueOr(evtData, "event", "").equals("chemical")) {
                            mcSubArr.add(evtData);
                        } // tillage event
                        else if (getValueOr(evtData, "event", "").equals("tillage")) {
                            mtSubArr.add(evtData);
//                        } // environment_modification event
//                        else if (getValueOr(evtData, "event", "").equals("environment_modification")) {
//                            meSubArr.add(evtData);
                        } // harvest event
                        else if (getValueOr(evtData, "event", "").equals("harvest")) {
                            mhSubArr.add(evtData);
                            if (!getValueOr(evtData, "date", "").trim().equals("")) {
                                smData.put("hadat_valid", "Y");
                            }
//                            copyItem(smData, evtData, "hadat", "date", false);
                        } else {
                        }
                    } else {
                    }

                }

                // Cancel for assume default value handling
//                // If alternative fields are avaiable for fertilizer data
//                if (mfSubArr.isEmpty()) {
//                    if (!getObjectOr(result, "fen_tot", "").equals("")
//                            || !getObjectOr(result, "fep_tot", "").equals("")
//                            || !getObjectOr(result, "fek_tot", "").equals("")) {
//                        mfSubArr.add(new HashMap());
//                    }
//                }
                
                // Specila handling for fallow experiment
                if (mpData.isEmpty()) {
                    isFallow = true;
                    cuData.put("crid", "FA");
                    cuData.put("dssat_cul_id", "IB0001");
                    cuData.put("cul_name", "Fallow");
                    if (mhSubArr.isEmpty()) {
                        HashMap mhData = new HashMap();
                        copyItem(mhData, rootData, "date", "endat", false);
    //                    mhData.put("hastg", "GS000");
                        mhSubArr.add(mhData);
                        smData.put("hadat_valid", "Y");
                    }
                }

                cuNum = setSecDataArr(cuData, cuArr);
                mpNum = setSecDataArr(mpData, mpArr);
                miNum = setSecDataArr(miSubArr, miArr);
                mfNum = setSecDataArr(mfSubArr, mfArr);
                mrNum = setSecDataArr(mrSubArr, mrArr);
                mcNum = setSecDataArr(mcSubArr, mcArr);
                mtNum = setSecDataArr(mtSubArr, mtArr);
                meNum = setSecDataArr(meSubArr, meArr);
                mhNum = setSecDataArr(mhSubArr, mhArr);
                smNum = setSecDataArr(smData, smArr);
                if (smNum == 0) {
                    smNum = 1;
                }

                sbData.append(String.format("%1$-3s%2$1s %3$1s %4$1s %5$-25s %6$2s %7$2s %8$2s %9$2s %10$2s %11$2s %12$2s %13$2s %14$2s %15$2s %16$2s %17$2s %18$2s\r\n",
                        String.format("%2s", getValueOr(sqData, "trno", "1")), // For 3-bit treatment number
                        getValueOr(sqData, "sq", "1").toString(), // P.S. default value here is based on document DSSAT vol2.pdf
                        getValueOr(sqData, "op", "1").toString(),
                        getValueOr(sqData, "co", "0").toString(),
                        formatStr(25, sqData, "trt_name", getValueOr(rootData, "trt_name", getValueOr(rootData, "exname", defValC))),
                        cuNum, //getObjectOr(data, "ge", defValI).toString(), 
                        flNum, //getObjectOr(data, "fl", defValI).toString(), 
                        saNum, //getObjectOr(data, "sa", defValI).toString(),
                        icNum, //getObjectOr(data, "ic", defValI).toString(),
                        mpNum, //getObjectOr(data, "pl", defValI).toString(),
                        miNum, //getObjectOr(data, "ir", defValI).toString(),
                        mfNum, //getObjectOr(data, "fe", defValI).toString(),
                        mrNum, //getObjectOr(data, "om", defValI).toString(),
                        mcNum, //getObjectOr(data, "ch", defValI).toString(),
                        mtNum, //getObjectOr(data, "ti", defValI).toString(),
                        meNum, //getObjectOr(data, "em", defValI).toString(),
                        mhNum, //getObjectOr(data, "ha", defValI).toString(),
                        smNum)); // 1

            }
            sbData.append("\r\n");

            // CULTIVARS Section
            if (!cuArr.isEmpty()) {
                sbData.append("*CULTIVARS\r\n");
                sbData.append("@C CR INGENO CNAME\r\n");

                for (int idx = 0; idx < cuArr.size(); idx++) {
                    HashMap secData = cuArr.get(idx);
//                    String cul_id = defValC;
                    String crid = getValueOr(secData, "crid", "");
                    // Checl if necessary data is missing
                    if (crid.equals("")) {
                        sbError.append("! Warning: Incompleted record because missing data : [crid]\r\n");
//                    } else {
//                        // Set cultivar id a default value deponds on the crop id
//                        if (crid.equals("MZ")) {
//                            cul_id = "990002";
//                        } else {
//                            cul_id = "999999";
//                        }
//                    }
//                    if (getObjectOr(secData, "cul_id", "").equals("")) {
//                        sbError.append("! Warning: Incompleted record because missing data : [cul_id], and will use default value '").append(cul_id).append("'\r\n");
                    }
                    sbData.append(String.format("%1$2s %2$-2s %3$-6s %4$s\r\n",
                            idx + 1,
                            formatStr(2, secData, "crid", defValBlank), // P.S. if missing, default value use blank string
                            formatStr(6, secData, "dssat_cul_id", getValueOr(secData, "cul_id", defValC)), // P.S. Set default value which is deponds on crid(Cancelled)
                            getValueOr(secData, "cul_name", defValC)));

                    if (!getValueOr(secData, "rm", "").equals("") || !getValueOr(secData, "cul_notes", "").equals("")) {
                        if (sbNotesData.toString().equals("")) {
                            sbNotesData.append("@NOTES\r\n");
                        }
                        sbNotesData.append(" Cultivar Additional Info\r\n");
                        sbNotesData.append(" C   RM CNAME            CUL_NOTES\r\n");
                        sbNotesData.append(String.format("%1$2s %2$4s %3$s\r\n",
                                idx + 1,
                                getValueOr(secData, "rm", defValC),
                                getValueOr(secData, "cul_notes", defValC)));
                    }

                }
                sbData.append("\r\n");
            } else if (!isFallow) {
                sbError.append("! Warning: There is no cultivar data in the experiment.\r\n");
            }

            // FIELDS Section
            if (!flArr.isEmpty()) {
                sbData.append("*FIELDS\r\n");
                sbData.append("@L ID_FIELD WSTA....  FLSA  FLOB  FLDT  FLDD  FLDS  FLST SLTX  SLDP  ID_SOIL    FLNAME\r\n");
                eventPart2 = new StringBuilder();
                eventPart2.append("@L ...........XCRD ...........YCRD .....ELEV .............AREA .SLEN .FLWR .SLAS FLHST FHDUR\r\n");
            } else {
                sbError.append("! Warning: There is no field data in the experiment.\r\n");
            }
            for (int idx = 0; idx < flArr.size(); idx++) {
                HashMap secData = flArr.get(idx);
                // Check if the necessary is missing
                if (getObjectOr(secData, "wst_id", "").equals("")) {
                    sbError.append("! Warning: Incompleted record because missing data : [wst_id]\r\n");
                }
                String soil_id = getValueOr(secData, "soil_id", defValC);
                if (soil_id.equals("")) {
                    sbError.append("! Warning: Incompleted record because missing data : [soil_id]\r\n");
                } else if (soil_id.length() > 10) {
                    sbError.append("! Warning: Oversized data : [soil_id] ").append(soil_id).append("\r\n");
                }
                sbData.append(String.format("%1$2s %2$-8s %3$-8s %4$5s %5$5s %6$-5s %7$5s %8$5s %9$-5s %10$-5s%11$5s  %12$-10s %13$s\r\n", // P.S. change length definition to match current way
                        idx + 1,
                        formatStr(8, secData, "id_field", defValC),
                        formatStr(8, secData, "wst_id", defValC),
                        formatStr(4, secData, "flsl", defValC),
                        formatNumStr(5, secData, "flob", defValR),
                        formatStr(5, secData, "fl_drntype", defValC),
                        formatNumStr(5, secData, "fldrd", defValR),
                        formatNumStr(5, secData, "fldrs", defValR),
                        formatStr(5, secData, "flst", defValC),
                        formatStr(5, transSltx(getValueOr(secData, "sltx", defValC)), "sltx"),
                        formatNumStr(5, secData, "sldp", defValR),
                        soil_id,
                        getValueOr(secData, "fl_name", defValC)));

                eventPart2.append(String.format("%1$2s %2$15s %3$15s %4$9s %5$17s %6$5s %7$5s %8$5s %9$5s %10$5s\r\n",
                        idx + 1,
                        formatNumStr(15, secData, "fl_long", defValR),
                        formatNumStr(15, secData, "fl_lat", defValR),
                        formatNumStr(9, secData, "flele", defValR),
                        formatNumStr(17, secData, "farea", defValR),
                        "-99", // P.S. SLEN keeps -99
                        formatNumStr(5, secData, "fllwr", defValR),
                        formatNumStr(5, secData, "flsla", defValR),
                        formatStr(5, secData, "flhst", defValC),
                        formatNumStr(5, secData, "fhdur", defValR)));
            }
            if (!flArr.isEmpty()) {
                sbData.append(eventPart2.toString()).append("\r\n");
            }

            // SOIL ANALYSIS Section
            if (!saArr.isEmpty()) {
                sbData.append("*SOIL ANALYSIS\r\n");

                for (int idx = 0; idx < saArr.size(); idx++) {

                    HashMap secData = (HashMap) saArr.get(idx);
                    sbData.append("@A SADAT  SMHB  SMPX  SMKE  SANAME\r\n");
                    sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$5s  %6$s\r\n",
                            idx + 1,
                            formatDateStr(getValueOr(secData, "sadat", defValD)),
                            getValueOr(secData, "samhb", defValC),
                            getValueOr(secData, "sampx", defValC),
                            getValueOr(secData, "samke", defValC),
                            getValueOr(secData, "sa_name", defValC)));

                    ArrayList<HashMap> subDataArr = getObjectOr(secData, "soilLayer", new ArrayList());
                    if (!subDataArr.isEmpty()) {
                        sbData.append("@A  SABL  SADM  SAOC  SANI SAPHW SAPHB  SAPX  SAKE  SASC\r\n");
                    }
                    for (int j = 0; j < subDataArr.size(); j++) {
                        HashMap subData = subDataArr.get(j);
                        sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$5s %6$5s %7$5s %8$5s %9$5s %10$5s\r\n",
                                idx + 1,
                                formatNumStr(5, subData, "sabl", defValR),
                                formatNumStr(5, subData, "sabdm", defValR),
                                formatNumStr(5, subData, "saoc", defValR),
                                formatNumStr(5, subData, "sani", defValR),
                                formatNumStr(5, subData, "saphw", defValR),
                                formatNumStr(5, subData, "saphb", defValR),
                                formatNumStr(5, subData, "sapx", defValR),
                                formatNumStr(5, subData, "sake", defValR),
                                formatNumStr(5, subData, "sasc", defValR)));
                    }


                }
                sbData.append("\r\n");
            }

            // INITIAL CONDITIONS Section
            if (!icArr.isEmpty()) {
                sbData.append("*INITIAL CONDITIONS\r\n");

                for (int idx = 0; idx < icArr.size(); idx++) {

                    HashMap secData = icArr.get(idx);
                    sbData.append("@C   PCR ICDAT  ICRT  ICND  ICRN  ICRE  ICWD ICRES ICREN ICREP ICRIP ICRID ICNAME\r\n");
                    sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$5s %6$5s %7$5s %8$5s %9$5s %10$5s %11$5s %12$5s %13$5s %14$s\r\n",
                            idx + 1,
                            translateTo2BitCrid(secData, "icpcr", defValC),
                            formatDateStr(getValueOr(secData, "icdat", getPdate(result))),
                            formatNumStr(5, secData, "icrt", defValR),
                            formatNumStr(5, secData, "icnd", defValR),
                            formatNumStr(5, secData, "icrz#", defValR),
                            formatNumStr(5, secData, "icrze", defValR),
                            formatNumStr(5, secData, "icwt", defValR),
                            formatNumStr(5, secData, "icrag", defValR),
                            formatNumStr(5, secData, "icrn", defValR),
                            formatNumStr(5, secData, "icrp", defValR),
                            formatNumStr(5, secData, "icrip", defValR),
                            formatNumStr(5, secData, "icrdp", defValR),
                            getValueOr(secData, "ic_name", defValC)));

                    ArrayList<HashMap> subDataArr = getObjectOr(secData, "soilLayer", new ArrayList());
                    if (!subDataArr.isEmpty()) {
                        sbData.append("@C  ICBL  SH2O  SNH4  SNO3\r\n");
                    }
                    for (int j = 0; j < subDataArr.size(); j++) {
                        HashMap subData = subDataArr.get(j);
                        sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$5s\r\n",
                                idx + 1,
                                formatNumStr(5, subData, "icbl", defValR),
                                formatNumStr(5, subData, "ich2o", defValR),
                                formatNumStr(5, subData, "icnh4", defValR),
                                formatNumStr(5, subData, "icno3", defValR)));
                    }
                }
                sbData.append("\r\n");
            }

            // PLANTING DETAILS Section
            if (!mpArr.isEmpty()) {
                sbData.append("*PLANTING DETAILS\r\n");
                sbData.append("@P PDATE EDATE  PPOP  PPOE  PLME  PLDS  PLRS  PLRD  PLDP  PLWT  PAGE  PENV  PLPH  SPRL                        PLNAME\r\n");

                for (int idx = 0; idx < mpArr.size(); idx++) {

                    HashMap secData = mpArr.get(idx);
                    // Check if necessary data is missing
                    String pdate = getValueOr(secData, "date", "");
                    if (pdate.equals("")) {
                        sbError.append("! Warning: Incompleted record because missing data : [pdate]\r\n");
                    } else if (formatDateStr(pdate).equals(defValD)) {
                        sbError.append("! Warning: Incompleted record because variable [pdate] with invalid value [").append(pdate).append("]\r\n");
                    }
                    if (getValueOr(secData, "plpop", getValueOr(secData, "plpoe", "")).equals("")) {
                        sbError.append("! Warning: Incompleted record because missing data : [plpop] and [plpoe]\r\n");
                    }
                    if (getValueOr(secData, "plrs", "").equals("")) {
                        sbError.append("! Warning: Incompleted record because missing data : [plrs]\r\n");
                    }
//                    if (getValueOr(secData, "plma", "").equals("")) {
//                        sbError.append("! Warning: missing data : [plma], and will automatically use default value 'S'\r\n");
//                    }
//                    if (getValueOr(secData, "plds", "").equals("")) {
//                        sbError.append("! Warning: missing data : [plds], and will automatically use default value 'R'\r\n");
//                    }
//                    if (getValueOr(secData, "pldp", "").equals("")) {
//                        sbError.append("! Warning: missing data : [pldp], and will automatically use default value '7'\r\n");
//                    }

                    // mm -> cm
                    String pldp = getValueOr(secData, "pldp", "");
                    if (!pldp.equals("")) {
                        try {
                            BigDecimal pldpBD = new BigDecimal(pldp);
                            pldpBD = pldpBD.divide(new BigDecimal("10"));
                            secData.put("pldp", pldpBD.toString());
                        } catch (NumberFormatException e) {
                        }
                    }

                    sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$5s %6$5s %7$5s %8$5s %9$5s %10$5s %11$5s %12$5s %13$5s %14$5s %15$5s                        %16$s\r\n",
                            idx + 1,
                            formatDateStr(getValueOr(secData, "date", defValD)),
                            formatDateStr(getValueOr(secData, "edate", defValD)),
                            formatNumStr(5, secData, "plpop", getValueOr(secData, "plpoe", defValR)),
                            formatNumStr(5, secData, "plpoe", getValueOr(secData, "plpop", defValR)),
                            getValueOr(secData, "plma", defValC), // P.S. Set default value as "S"(Cancelled)
                            getValueOr(secData, "plds", defValC), // P.S. Set default value as "R"(Cancelled)
                            formatNumStr(5, secData, "plrs", defValR),
                            formatNumStr(5, secData, "plrd", defValR),
                            formatNumStr(5, secData, "pldp", defValR), // P.S. Set default value as "7"(Cancelled)
                            formatNumStr(5, secData, "plmwt", defValR),
                            formatNumStr(5, secData, "page", defValR),
                            formatNumStr(5, secData, "plenv", defValR),
                            formatNumStr(5, secData, "plph", defValR),
                            formatNumStr(5, secData, "plspl", defValR),
                            getValueOr(secData, "pl_name", defValC)));

                }
                sbData.append("\r\n");
            } else if (!isFallow) {
                sbError.append("! Warning: There is no plainting data in the experiment.\r\n");
            }

            // IRRIGATION AND WATER MANAGEMENT Section
            if (!miArr.isEmpty()) {
                sbData.append("*IRRIGATION AND WATER MANAGEMENT\r\n");

                for (int idx = 0; idx < miArr.size(); idx++) {

//                    secData = (ArrayList) miArr.get(idx);
                    ArrayList<HashMap> subDataArr = miArr.get(idx);
                    HashMap subData;
                    if (!subDataArr.isEmpty()) {
                        subData = subDataArr.get(0);
                    } else {
                        subData = new HashMap();
                    }
                    sbData.append("@I  EFIR  IDEP  ITHR  IEPT  IOFF  IAME  IAMT IRNAME\r\n");
                    sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$5s %6$5s %7$5s %8$5s %9$s\r\n",
                            idx + 1,
                            formatNumStr(5, subData, "ireff", defValR),
                            formatNumStr(5, subData, "irmdp", defValR),
                            formatNumStr(5, subData, "irthr", defValR),
                            formatNumStr(5, subData, "irept", defValR),
                            getValueOr(subData, "irstg", defValC),
                            getValueOr(subData, "iame", defValC),
                            formatNumStr(5, subData, "iamt", defValR),
                            getValueOr(subData, "ir_name", defValC)));

                    if (!subDataArr.isEmpty()) {
                        sbData.append("@I IDATE  IROP IRVAL\r\n");
                    }
                    for (int j = 0; j < subDataArr.size(); j++) {
                        subData = subDataArr.get(j);
                        sbData.append(String.format("%1$2s %2$5s %3$-5s %4$5s\r\n",
                                idx + 1,
                                formatDateStr(getValueOr(subData, "date", defValD)), // P.S. idate -> date
                                getValueOr(subData, "irop", defValC),
                                formatNumStr(5, subData, "irval", defValR)));
                    }
                }
                sbData.append("\r\n");
            }

            // FERTILIZERS (INORGANIC) Section
            if (!mfArr.isEmpty()) {
                sbData.append("*FERTILIZERS (INORGANIC)\r\n");
                sbData.append("@F FDATE  FMCD  FACD  FDEP  FAMN  FAMP  FAMK  FAMC  FAMO  FOCD FERNAME\r\n");
//                String fen_tot = getValueOr(result, "fen_tot", defValR);
//                String fep_tot = getValueOr(result, "fep_tot", defValR);
//                String fek_tot = getValueOr(result, "fek_tot", defValR);
//                String pdate = getPdate(result);
//                if (pdate.equals("")) {
//                    pdate = defValD;
//                }

                for (int idx = 0; idx < mfArr.size(); idx++) {
                    ArrayList<HashMap> secDataArr = mfArr.get(idx);

                    for (int i = 0; i < secDataArr.size(); i++) {
                        HashMap secData = secDataArr.get(i);
//                        if (getValueOr(secData, "fdate", "").equals("")) {
//                            sbError.append("! Warning: missing data : [fdate], and will automatically use planting value '").append(pdate).append("'\r\n");
//                        }
//                        if (getValueOr(secData, "fecd", "").equals("")) {
//                            sbError.append("! Warning: missing data : [fecd], and will automatically use default value 'FE001'\r\n");
//                        }
//                        if (getValueOr(secData, "feacd", "").equals("")) {
//                            sbError.append("! Warning: missing data : [feacd], and will automatically use default value 'AP002'\r\n");
//                        }
//                        if (getValueOr(secData, "fedep", "").equals("")) {
//                            sbError.append("! Warning: missing data : [fedep], and will automatically use default value '10'\r\n");
//                        }
//                        if (getValueOr(secData, "feamn", "").equals("")) {
//                            sbError.append("! Warning: missing data : [feamn], and will automatically use the value of FEN_TOT, '").append(fen_tot).append("'\r\n");
//                        }
//                        if (getValueOr(secData, "feamp", "").equals("")) {
//                            sbError.append("! Warning: missing data : [feamp], and will automatically use the value of FEP_TOT, '").append(fep_tot).append("'\r\n");
//                        }
//                        if (getValueOr(secData, "feamk", "").equals("")) {
//                            sbError.append("! Warning: missing data : [feamk], and will automatically use the value of FEK_TOT, '").append(fek_tot).append("'\r\n");
//                        }
                        sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$5s %6$5s %7$5s %8$5s %9$5s %10$5s %11$5s %12$s\r\n",
                                idx + 1,
                                formatDateStr(getValueOr(secData, "date", defValD)), // P.S. fdate -> date
                                getValueOr(secData, "fecd", defValC), // P.S. Set default value as "FE005"(Cancelled)
                                getValueOr(secData, "feacd", defValC), // P.S. Set default value as "AP002"(Cancelled)
                                formatNumStr(5, secData, "fedep", defValR), // P.S. Set default value as "10"(Cancelled)
                                formatNumStr(5, secData, "feamn", defValR), // P.S. Set default value to use the value of FEN_TOT in meta data(Cancelled)
                                formatNumStr(5, secData, "feamp", defValR), // P.S. Set default value to use the value of FEP_TOT in meta data(Cancelled)
                                formatNumStr(5, secData, "feamk", defValR), // P.S. Set default value to use the value of FEK_TOT in meta data(Cancelled)
                                formatNumStr(5, secData, "feamc", defValR),
                                formatNumStr(5, secData, "feamo", defValR),
                                getValueOr(secData, "feocd", defValC),
                                getValueOr(secData, "fe_name", defValC)));

                    }
                }
                sbData.append("\r\n");
            }

            // RESIDUES AND ORGANIC FERTILIZER Section
            if (!mrArr.isEmpty()) {
                sbData.append("*RESIDUES AND ORGANIC FERTILIZER\r\n");
                sbData.append("@R RDATE  RCOD  RAMT  RESN  RESP  RESK  RINP  RDEP  RMET RENAME\r\n");

                for (int idx = 0; idx < mrArr.size(); idx++) {
                    ArrayList<HashMap> secDataArr = mrArr.get(idx);

                    for (int i = 0; i < secDataArr.size(); i++) {
                        HashMap secData =  secDataArr.get(i);
                        sbData.append(String.format("%1$2s %2$5s %3$-5s %4$5s %5$5s %6$5s %7$5s %8$5s %9$5s %10$5s %11$s\r\n",
                                idx + 1,
                                formatDateStr(getValueOr(secData, "date", defValD)), // P.S. omdat -> date
                                getValueOr(secData, "omcd", defValC),
                                formatNumStr(5, secData, "omamt", defValR),
                                formatNumStr(5, secData, "omn%", defValR),
                                formatNumStr(5, secData, "omp%", defValR),
                                formatNumStr(5, secData, "omk%", defValR),
                                formatNumStr(5, secData, "ominp", defValR),
                                formatNumStr(5, secData, "omdep", defValR),
                                formatNumStr(5, secData, "omacd", defValR),
                                getValueOr(secData, "om_name", defValC)));
                    }
                }
                sbData.append("\r\n");
            }

            // CHEMICAL APPLICATIONS Section
            if (!mcArr.isEmpty()) {
                sbData.append("*CHEMICAL APPLICATIONS\r\n");
                sbData.append("@C CDATE CHCOD CHAMT  CHME CHDEP   CHT..CHNAME\r\n");

                for (int idx = 0; idx < mcArr.size(); idx++) {
                    ArrayList<HashMap> secDataArr = mcArr.get(idx);

                    for (int i = 0; i < secDataArr.size(); i++) {
                        HashMap secData = secDataArr.get(i);
                        sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$5s %6$5s %7$5s  %8$s\r\n",
                                idx + 1,
                                formatDateStr(getValueOr(secData, "date", defValD)), // P.S. cdate -> date
                                getValueOr(secData, "chcd", defValC),
                                formatNumStr(5, secData, "chamt", defValR),
                                getValueOr(secData, "chacd", defValC),
                                getValueOr(secData, "chdep", defValC),
                                getValueOr(secData, "ch_targets", defValC),
                                getValueOr(secData, "ch_name", defValC)));
                    }
                }
                sbData.append("\r\n");
            }

            // TILLAGE Section
            if (!mtArr.isEmpty()) {
                sbData.append("*TILLAGE AND ROTATIONS\r\n");
                sbData.append("@T TDATE TIMPL  TDEP TNAME\r\n");

                for (int idx = 0; idx < mtArr.size(); idx++) {
                    ArrayList<HashMap> secDataArr = mtArr.get(idx);

                    for (int i = 0; i < secDataArr.size(); i++) {
                        HashMap secData = secDataArr.get(i);
                        sbData.append(String.format("%1$2s %2$5s %3$5s %4$5s %5$s\r\n",
                                idx + 1,
                                formatDateStr(getValueOr(secData, "date", defValD)), // P.S. tdate -> date
                                getValueOr(secData, "tiimp", defValC),
                                formatNumStr(5, secData, "tidep", defValR),
                                getValueOr(secData, "ti_name", defValC)));

                    }
                }
                sbData.append("\r\n");
            }

            // ENVIRONMENT MODIFICATIONS Section
            if (!meArr.isEmpty()) {
                sbData.append("*ENVIRONMENT MODIFICATIONS\r\n");
                sbData.append("@E ODATE EDAY  ERAD  EMAX  EMIN  ERAIN ECO2  EDEW  EWIND ENVNAME\r\n");

                for (int idx = 0, cnt = 1; idx < meArr.size(); idx++) {
                    ArrayList<HashMap> secDataArr = meArr.get(idx);
                    for (int i = 0; i < secDataArr.size(); i++) {
                        HashMap secData = secDataArr.get(i);
                        sbData.append(String.format("%1$2s%2$s\r\n",
                                cnt,
                                secData.get("em_data")));
//                        sbData.append(String.format("%1$2s%2$s\r\n",
//                                idx + 1,
//                                (String) secDataArr.get(i)));
//                        sbData.append(String.format("%1$2s %2$5s %3$-1s%4$4s %5$-1s%6$4s %7$-1s%8$4s %9$-1s%10$4s %11$-1s%12$4s %13$-1s%14$4s %15$-1s%16$4s %17$-1s%18$4s %19$s\r\n",
//                                idx + 1,
//                                formatDateStr(getValueOr(secData, "date", defValD).toString()), // P.S. emday -> date
//                                getValueOr(secData, "ecdyl", defValBlank),
//                                formatNumStr(4, getValueOr(secData, "emdyl", defValR),
//                                getValueOr(secData, "ecrad", defValBlank),
//                                formatNumStr(4, getValueOr(secData, "emrad", defValR),
//                                getValueOr(secData, "ecmax", defValBlank),
//                                formatNumStr(4, getValueOr(secData, "emmax", defValR),
//                                getValueOr(secData, "ecmin", defValBlank),
//                                formatNumStr(4, getValueOr(secData, "emmin", defValR),
//                                getValueOr(secData, "ecrai", defValBlank),
//                                formatNumStr(4, getValueOr(secData, "emrai", defValR),
//                                getValueOr(secData, "ecco2", defValBlank),
//                                formatNumStr(4, getValueOr(secData, "emco2", defValR),
//                                getValueOr(secData, "ecdew", defValBlank),
//                                formatNumStr(4, getValueOr(secData, "emdew", defValR),
//                                getValueOr(secData, "ecwnd", defValBlank),
//                                formatNumStr(4, getValueOr(secData, "emwnd", defValR),
//                                getValueOr(secData, "em_name", defValC)));
                    }
                }
                sbData.append("\r\n");
            }

            // HARVEST DETAILS Section
            if (!mhArr.isEmpty()) {
                sbData.append("*HARVEST DETAILS\r\n");
                sbData.append("@H HDATE  HSTG  HCOM HSIZE   HPC  HBPC HNAME\r\n");

                for (int idx = 0; idx < mhArr.size(); idx++) {
                    ArrayList<HashMap> secDataArr = mhArr.get(idx);

                    for (int i = 0; i < secDataArr.size(); i++) {
                        HashMap secData = secDataArr.get(i);
                        sbData.append(String.format("%1$2s %2$5s %3$-5s %4$-5s %5$-5s %6$5s %7$5s %8$s\r\n",
                                idx + 1,
                                formatDateStr(getValueOr(secData, "date", defValD)), // P.S. hdate -> date
                                getValueOr(secData, "hastg", defValC),
                                getValueOr(secData, "hacom", defValC),
                                getValueOr(secData, "hasiz", defValC),
                                formatNumStr(5, secData, "hap%", defValR),
                                formatNumStr(5, secData, "hab%", defValR),
                                getValueOr(secData, "ha_name", defValC)));

                    }
                }
                sbData.append("\r\n");
            }

            // SIMULATION CONTROLS and AUTOMATIC MANAGEMENT Section
            if (!smArr.isEmpty()) {

                // Loop all the simulation control records
                sbData.append("*SIMULATION CONTROLS\r\n");
                for (int idx = 0; idx < smArr.size(); idx++) {
                    HashMap secData = smArr.get(idx);
                    sbData.append(createSMMAStr(idx + 1, secData));
                }

            } else {
                sbData.append("*SIMULATION CONTROLS\r\n");
                sbData.append(createSMMAStr(1, new HashMap()));
            }

            // DOME Info Section
            if (isAnyDomeApplied) {
                sbDomeData.append("! APPLIED DOME INFO\r\n");
                for (String exname : appliedDomes.keySet()) {
                    if (!getValueOr(appliedDomes, exname, "").equals("")) {
                        sbDomeData.append("! ").append(exname).append("\t");
                        sbDomeData.append(appliedDomes.get(exname));
                        sbDomeData.append("\r\n");
                    }
                }
            }

            // Output finish
            bwX.write(sbError.toString());
            bwX.write(sbDomeData.toString());
            bwX.write(sbGenData.toString());
            bwX.write(sbNotesData.toString());
            bwX.write(sbData.toString());
            bwX.close();
        } catch (IOException e) {
            LOG.error(DssatCommonOutput.getStackTrace(e));
        }
    }

    /**
     * Create string of Simulation Control and Automatic Management Section
     *
     * @param smid simulation index number
     * @param trData date holder for one treatment data
     * @return date string with format of "yyddd"
     */
    private String createSMMAStr(int smid, HashMap trData) {

        StringBuilder sb = new StringBuilder();
        String nitro = "Y";
        String water = "Y";
        String co2 = "M";
        String harOpt = "M";
        String sdate;
        String sm = String.format("%2d", smid);
//        ArrayList<HashMap> dataArr;
        HashMap subData;

//        // Check if the meta data of fertilizer is not "N" ("Y" or null)
//        if (!getValueOr(expData, "fertilizer", "").equals("N")) {
//
//            // Check if necessary data is missing in all the event records
//            // P.S. rule changed since all the necessary data has a default value for it
//            dataArr = getObjectOr(trData, "fertilizer", new ArrayList());
//            if (dataArr.isEmpty()) {
//                nitro = "N";
//            }
////            for (int i = 0; i < dataArr.size(); i++) {
////                subData = dataArr.get(i);
////                if (getValueOr(subData, "date", "").equals("")
////                        || getValueOr(subData, "fecd", "").equals("")
////                        || getValueOr(subData, "feacd", "").equals("")
////                        || getValueOr(subData, "feamn", "").equals("")) {
////                    nitro = "N";
////                    break;
////                }
////            }
//        }

//        // Check if the meta data of irrigation is not "N" ("Y" or null)
//        if (!getValueOr(expData, "irrigation", "").equals("N")) {
//
//            // Check if necessary data is missing in all the event records
//            dataArr = getObjectOr(trData, "irrigation", new ArrayList());
//            for (int i = 0; i < dataArr.size(); i++) {
//                subData = dataArr.get(i);
//                if (getValueOr(subData, "date", "").equals("")
//                        || getValueOr(subData, "irval", "").equals("")) {
//                    water = "N";
//                    break;
//                }
//            }
//        }

        // Check if CO2Y value is provided and the value is positive, then set CO2 switch to W
        String co2y = getValueOr(trData, "co2y", "").trim();
        if (!co2y.equals("") && !co2y.startsWith("-")) {
            co2 = "W";
        }

        sdate = getValueOr(trData, "sdat", "").toString();
        if (sdate.equals("")) {
            subData = getObjectOr(trData, "planting", new HashMap());
            sdate = getValueOr(subData, "date", defValD);
        }
        sdate = formatDateStr(sdate);
        sdate = String.format("%5s", sdate);

        if (!getValueOr(trData, "hadat_valid", "").trim().equals("")) {
            harOpt = "R";
        }

        String smStr;
        // GENERAL
        sb.append("@N GENERAL     NYERS NREPS START SDATE RSEED SNAME....................\r\n");
        if (!(smStr = getValueOr(trData, "sm_general", "")).equals("")) {
            if (!sdate.trim().equals("-99") && !sdate.trim().equals("")) {
                smStr = replaceSMStr(smStr, sdate, 30);
            }
            sb.append(sm).append(" ").append(smStr).append("\r\n");
        } else {
            sb.append(sm).append(" GE              1     1     S ").append(sdate).append("  2150 DEFAULT SIMULATION CONTROL\r\n");
        }
        // OPTIONS
        sb.append("@N OPTIONS     WATER NITRO SYMBI PHOSP POTAS DISES  CHEM  TILL   CO2\r\n");
        if (!(smStr = getValueOr(trData, "sm_options", "")).equals("")) {
//            smStr = replaceSMStr(smStr, co2, 64);
            sb.append(sm).append(" ").append(smStr).append("\r\n");
        } else {
            sb.append(sm).append(" OP              ").append(water).append("     ").append(nitro).append("     Y     N     N     N     N     Y     ").append(co2).append("\r\n");
        }
        // METHODS
        sb.append("@N METHODS     WTHER INCON LIGHT EVAPO INFIL PHOTO HYDRO NSWIT MESOM MESEV MESOL\r\n");
        if (!(smStr = getValueOr(trData, "sm_methods", "")).equals("")) {
            sb.append(sm).append(" ").append(smStr).append("\r\n");
        } else {
            sb.append(sm).append(" ME              M     M     E     R     S     L     R     1     P     S     2\r\n"); // P.S. 2012/09/02 MESOM "G" -> "P"
        }
        // MANAGEMENT
        sb.append("@N MANAGEMENT  PLANT IRRIG FERTI RESID HARVS\r\n");
        if (!(smStr = getValueOr(trData, "sm_management", "")).equals("")) {
//            smStr = smStr.replaceAll("     D", "     R");
//            smStr = replaceSMStr(smStr, harOpt, 40);
            sb.append(sm).append(" ").append(smStr).append("\r\n");
        } else {
            sb.append(sm).append(" MA              R     R     R     R     ").append(harOpt).append("\r\n");
        }
        // OUTPUTS
        sb.append("@N OUTPUTS     FNAME OVVEW SUMRY FROPT GROUT CAOUT WAOUT NIOUT MIOUT DIOUT VBOSE CHOUT OPOUT\r\n");
        if (!(smStr = getValueOr(trData, "sm_outputs", "")).equals("")) {
            sb.append(sm).append(" ").append(smStr).append("\r\n\r\n");
        } else {
            sb.append(sm).append(" OU              N     Y     Y     1     Y     Y     N     N     N     N     N     N     N\r\n\r\n");
        }
        // PLANTING
        sb.append("@  AUTOMATIC MANAGEMENT\r\n");
        sb.append("@N PLANTING    PFRST PLAST PH2OL PH2OU PH2OD PSTMX PSTMN\r\n");
        if (!(smStr = getValueOr(trData, "sm_planting", "")).equals("")) {
            sb.append(sm).append(" ").append(smStr).append("\r\n");
        } else {
            sb.append(sm).append(" PL          82050 82064    40   100    30    40    10\r\n");
        }
        // IRRIGATION
        sb.append("@N IRRIGATION  IMDEP ITHRL ITHRU IROFF IMETH IRAMT IREFF\r\n");
        if (!(smStr = getValueOr(trData, "sm_irrigation", "")).equals("")) {
            sb.append(sm).append(" ").append(smStr).append("\r\n");
        } else {
            sb.append(sm).append(" IR             30    50   100 GS000 IR001    10  1.00\r\n");
        }
        // NITROGEN
        sb.append("@N NITROGEN    NMDEP NMTHR NAMNT NCODE NAOFF\r\n");
        if (!(smStr = getValueOr(trData, "sm_nitrogen", "")).equals("")) {
            sb.append(sm).append(" ").append(smStr).append("\r\n");
        } else {
            sb.append(sm).append(" NI             30    50    25 FE001 GS000\r\n");
        }
        // RESIDUES
        sb.append("@N RESIDUES    RIPCN RTIME RIDEP\r\n");
        if (!(smStr = getValueOr(trData, "sm_residues", "")).equals("")) {
            sb.append(sm).append(" ").append(smStr).append("\r\n");
        } else {
            sb.append(sm).append(" RE            100     1    20\r\n");
        }
        // HARVEST
        sb.append("@N HARVEST     HFRST HLAST HPCNP HPCNR\r\n");
        if (!(smStr = getValueOr(trData, "sm_harvests", "")).equals("")) {
            sb.append(sm).append(" ").append(smStr).append("\r\n\r\n");
        } else {
            sb.append(sm).append(" HA              0 83057   100     0\r\n\r\n");
        }

        return sb.toString();
    }

    /**
     * Get index value of the record and set new id value in the array
     *
     * @param m sub data
     * @param arr array of sub data
     * @return current index value of the sub data
     */
    private int setSecDataArr(HashMap m, ArrayList arr) {

        if (!m.isEmpty()) {
            for (int j = 0; j < arr.size(); j++) {
                if (arr.get(j).equals(m)) {
                    return j + 1;
                }
            }
            arr.add(m);
            return arr.size();
        } else {
            return 0;
        }
    }

    /**
     * Get index value of the record and set new id value in the array
     *
     * @param inArr sub array of data
     * @param outArr array of sub data
     * @return current index value of the sub data
     */
    private int setSecDataArr(ArrayList inArr, ArrayList outArr) {

        if (!inArr.isEmpty()) {
            for (int j = 0; j < outArr.size(); j++) {
                if (outArr.get(j).equals(inArr)) {
                    return j + 1;
                }
            }
            outArr.add(inArr);
            return outArr.size();
        } else {
            return 0;
        }
    }

    /**
     * To check if there is plot info data existed in the experiment
     *
     * @param expData experiment data holder
     * @return the boolean value for if plot info exists
     */
    private boolean isPlotInfoExist(Map expData) {

        String[] plotIds = {"plta", "pltr#", "pltln", "pldr", "pltsp", "pllay", "pltha", "plth#", "plthl", "plthm"};
        for (int i = 0; i < plotIds.length; i++) {
            if (!getValueOr(expData, plotIds[i], "").equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * To check if there is soil analysis info data existed in the experiment
     *
     * @param expData initial condition layer data array
     * @return the boolean value for if soil analysis info exists
     */
    private boolean isSoilAnalysisExist(ArrayList<HashMap> icSubArr) {

        for (int i = 0; i < icSubArr.size(); i++) {
            if (!getValueOr(icSubArr.get(i), "slsc", "").equals("")) {
                return true;
            }

        }
        return false;
    }

    /**
     * Get sub data array from experiment data object
     *
     * @param expData experiment data holder
     * @param blockName top level block name
     * @param dataListName sub array name
     * @return sub data array
     */
    private ArrayList<HashMap> getDataList(Map expData, String blockName, String dataListName) {
        HashMap dataBlock = getObjectOr(expData, blockName, new HashMap());
        return getObjectOr(dataBlock, dataListName, new ArrayList<HashMap>());
    }

    /**
     * Try to translate 3-bit CRID to 2-bit version stored in the map
     *
     * @param cuData the cultivar data record
     * @param id the field id for contain crop id info
     * @param defVal the default value when id is not available
     * @return 2-bit crop ID
     */
    private String translateTo2BitCrid(Map cuData, String id, String defVal) {
        String crid = getValueOr(cuData, id, "");
        if (!crid.equals("")) {
            return DssatCRIDHelper.get2BitCrid(crid);
        } else {
            return defVal;
        }
    }

    /**
     * Try to translate 3-bit CRID to 2-bit version stored in the map
     *
     * @param cuData the cultivar data record
     */
    private void translateTo2BitCrid(Map cuData) {
        String crid = getValueOr(cuData, "crid", "");
        if (!crid.equals("")) {
            cuData.put("crid", DssatCRIDHelper.get2BitCrid(crid));
        }
    }

    /**
     * Get soil/weather data from data holder
     *
     * @param expData The experiment data holder
     * @param key The key name for soil/weather section
     * @return the list of data holder
     */
    private ArrayList readSWData(HashMap expData, String key) {
        ArrayList ret;
        Object soil = expData.get(key);
        if (soil != null) {
            if (soil instanceof ArrayList) {
                ret = (ArrayList) soil;
            } else {
                ret = new ArrayList();
                ret.add(soil);
            }
        } else {
            ret = new ArrayList();
        }

        return ret;
    }

    private String replaceSMStr(String smStr, String val, int start) {
        if (smStr.length() < start) {
            smStr = String.format("%-" + start + "s", smStr);
        }
        smStr = smStr.substring(0, start)
                + val
                + smStr.substring(start + val.length());
        return smStr;
    }

    private String getAppliedDomes(HashMap data, String domeType) {
        if (getValueOr(data, domeType + "_dome_applied", "").equals("Y")) {
            // Get dome ids
            String domeKey = "";
            if (domeType.equals("field")) {
                domeKey = "field_overlay";
            } else if (domeType.equals("seasonal")) {
                domeKey = "seasonal_strategy";
            }
            String[] domeIds = getValueOr(data, domeKey, "").split("[|]");
            String[] failedDomeIds = getValueOr(data, domeType + "_dome_failed", "").split("[|]");
            HashSet<String> domes = new HashSet();
            // Add all dome ids
            for (int j = 0; j < domeIds.length; j++) {
                if (!domeIds[j].equals("")) {
                    domes.add(domeIds[j]);
                }
            }
            // Remove failed dome id
            for (int j = 0; j < failedDomeIds.length; j++) {
                if (!failedDomeIds[j].equals("")) {
                    domes.remove(failedDomeIds[j]);
                }
            }
            // Convert to string format, divide by "|"
            StringBuilder ret = new StringBuilder();
            String[] domeArr = domes.toArray(new String[0]);
            if (domeArr.length > 0) {
                ret.append(domeArr[0]);
            }
            for (int i = 1; i < domeArr.length; i++) {
                ret.append("|").append(domeArr[i]);
            }
            return ret.toString();
        } else {
            return "";
        }
    }
}
