package org.agmip.translators.dssat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import org.agmip.util.JSONAdapter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
/**
 *
 * @author Meng Zhang
 */
public class DssatControllerTest {

    DssatControllerOutput obDssatControllerOutput;
    DssatControllerInput obDssatControllerInput;

    @Before
    public void setUp() throws Exception {
        obDssatControllerOutput = new DssatControllerOutput();
        obDssatControllerInput = new DssatControllerInput();
    }

    @Test
    public void test() throws IOException, Exception {
//        String jsonExample = "{\"observed\": {\"hwah\": 2929.0, \"adap\": 76.0, \"cwam\": 5532.0, \"mdap\": 129.0}, \"management\": {\"plrs\": 61.0, \"plpoe\": 7.2, \"hacom\": \"H\", \"icpcr\": \"MZ\", \"nnumm\": 3.0, \"CropMgmt\": [{\"fedep\": 10.0, \"feamn\": 27.0, \"feacd\": \"AP001\", \"feamk\": 0.0, \"fecd\": \"FE001\", \"feamp\": 0.0, \"fdate\": \"4/7/82\"}, {\"fedep\": 10.0, \"feamn\": 35.0, \"feacd\": \"AP001\", \"feamk\": 0.0, \"fecd\": \"FE001\", \"feamp\": 0.0, \"fdate\": \"4/12/82\"}, {\"fedep\": 10.0, \"feamn\": 54.0, \"feacd\": \"AP001\", \"feamk\": 0.0, \"fecd\": \"FE001\", \"feamp\": 0.0, \"fdate\": \"5/17/82\"}, {\"fedep\": \"\", \"feamn\": \"\", \"feacd\": \"\", \"feamk\": \"\", \"fecd\": \"\", \"feamp\": \"\", \"fdate\": \"\"}, {\"fedep\": \"\", \"feamn\": \"\", \"feacd\": \"\", \"feamk\": \"\", \"fecd\": \"\", \"feamp\": \"\", \"fdate\": \"\"}, {\"fedep\": \"\", \"feamn\": \"\", \"feacd\": \"\", \"feamk\": \"\", \"fecd\": \"\", \"feamp\": \"\", \"fdate\": \"\"}], \"fen_tot\": 116.0}, \"meta\": {\"cul_id\": \"IB0035\", \"people\": \" BENNET,J.M. ZUR,B. HAMMOND,L.C. JONES,J.W.\", \"fl_long\": -82.37, \"cr\": \"MZ\", \"institutes\": \"University of Florida\", \"main_factor\": \"NxW\", \"num_years\": 1.0, \"wst_long\": -82.37, \"wst_source\": \"Measured\", \"data_source\": \"DSSAT\", \"exname\": \"UFGA8201.MZX\", \"flele\": 40.0, \"fertilizer\": \"P\", \"wst_insi\": \"UFGA\", \"wst_dist\": 0.0, \"fl_loc_1\": \"USA\", \"fl_loc_2\": \"FLA\", \"fl_loc_3\": \"Gainesville\", \"soil_id\": \"IBMZ910014\", \"hdate\": \"19820704\", \"fl_name\": \"Irrigation Park\", \"pdate\": \"19820225\", \"wst_name\": \"Gainesville, FL\", \"exp_factors\": \"N; W\", \"id_field\": \"UFGA0002\", \"sltx\": \"LS\", \"eid\": 1.0, \"local_name\": \"Gainesville, FL\", \"irrig\": \"N\", \"distribution\": \"None\", \"wst_lat\": 29.63, \"fl_lat\": 29.63, \"sl_source\": \"SCS\"}, \"weather\": {\"tav\": 20.9, \"wndht\": 3.0, \"wst_name\": \"Gainesville, FL\", \"refht\": 2.0, \"elev\": 10.0, \"wst_insi\": \"UFGA\", \"WeatherDaily\": [{\"w_date\": \"19820101\", \"srad\": 5.9, \"tmax\": 24.4, \"tmin\": 15.6, \"rain\": 19.0}, {\"w_date\": \"19820102\", \"srad\": 7.0, \"tmax\": 22.2, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"1/3/82\", \"srad\": 9.0, \"tmax\": 27.8, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"1/4/82\", \"srad\": 3.1, \"tmax\": 26.1, \"tmin\": 15.0, \"rain\": 9.4}, {\"w_date\": \"1/5/82\", \"srad\": 12.9, \"tmax\": 17.2, \"tmin\": 2.2, \"rain\": 0.0}, {\"w_date\": \"1/6/82\", \"srad\": 11.4, \"tmax\": 25.0, \"tmin\": 4.4, \"rain\": 0.0}, {\"w_date\": \"1/7/82\", \"srad\": 8.5, \"tmax\": 26.7, \"tmin\": 11.7, \"rain\": 0.0}, {\"w_date\": \"1/8/82\", \"srad\": 3.0, \"tmax\": 22.8, \"tmin\": 14.4, \"rain\": 5.3}, {\"w_date\": \"1/9/82\", \"srad\": 14.4, \"tmax\": 16.7, \"tmin\": 8.9, \"rain\": 0.0}, {\"w_date\": \"1/10/82\", \"srad\": 12.0, \"tmax\": 14.4, \"tmin\": 1.1, \"rain\": 0.0}, {\"w_date\": \"1/11/82\", \"srad\": 15.0, \"tmax\": 14.4, \"tmin\": -6.7, \"rain\": 0.0}, {\"w_date\": \"1/12/82\", \"srad\": 10.8, \"tmax\": 10.0, \"tmin\": -7.8, \"rain\": 0.0}, {\"w_date\": \"1/13/82\", \"srad\": 4.8, \"tmax\": 20.0, \"tmin\": 3.9, \"rain\": 19.8}, {\"w_date\": \"1/14/82\", \"srad\": 6.2, \"tmax\": 18.9, \"tmin\": 6.1, \"rain\": 81.0}, {\"w_date\": \"1/15/82\", \"srad\": 14.4, \"tmax\": 12.2, \"tmin\": -3.3, \"rain\": 0.0}, {\"w_date\": \"1/16/82\", \"srad\": 12.6, \"tmax\": 19.4, \"tmin\": -1.7, \"rain\": 0.0}, {\"w_date\": \"1/17/82\", \"srad\": 14.6, \"tmax\": 19.4, \"tmin\": 5.6, \"rain\": 2.8}, {\"w_date\": \"1/18/82\", \"srad\": 11.2, \"tmax\": 20.6, \"tmin\": 1.1, \"rain\": 0.0}, {\"w_date\": \"1/19/82\", \"srad\": 13.3, \"tmax\": 25.6, \"tmin\": 8.9, \"rain\": 0.0}, {\"w_date\": \"1/20/82\", \"srad\": 13.4, \"tmax\": 27.8, \"tmin\": 8.3, \"rain\": 0.0}, {\"w_date\": \"1/21/82\", \"srad\": 11.6, \"tmax\": 27.8, \"tmin\": 10.0, \"rain\": 0.0}, {\"w_date\": \"1/22/82\", \"srad\": 10.7, \"tmax\": 25.6, \"tmin\": 12.2, \"rain\": 0.0}, {\"w_date\": \"1/23/82\", \"srad\": 10.1, \"tmax\": 26.1, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"1/24/82\", \"srad\": 12.9, \"tmax\": 23.9, \"tmin\": 10.0, \"rain\": 0.0}, {\"w_date\": \"1/25/82\", \"srad\": 14.1, \"tmax\": 21.1, \"tmin\": 0.0, \"rain\": 0.0}, {\"w_date\": \"1/26/82\", \"srad\": 14.2, \"tmax\": 20.0, \"tmin\": 4.4, \"rain\": 0.0}, {\"w_date\": \"1/27/82\", \"srad\": 13.9, \"tmax\": 15.6, \"tmin\": -1.1, \"rain\": 0.0}, {\"w_date\": \"1/28/82\", \"srad\": 13.8, \"tmax\": 21.7, \"tmin\": 2.2, \"rain\": 0.0}, {\"w_date\": \"1/29/82\", \"srad\": 13.3, \"tmax\": 22.2, \"tmin\": 3.3, \"rain\": 0.0}, {\"w_date\": \"1/30/82\", \"srad\": 12.5, \"tmax\": 25.6, \"tmin\": 8.9, \"rain\": 0.0}, {\"w_date\": \"1/31/82\", \"srad\": 12.6, \"tmax\": 28.9, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"2/1/82\", \"srad\": 14.0, \"tmax\": 26.1, \"tmin\": 12.2, \"rain\": 0.3}, {\"w_date\": \"2/2/82\", \"srad\": 7.6, \"tmax\": 26.7, \"tmin\": 12.8, \"rain\": 2.0}, {\"w_date\": \"2/3/82\", \"srad\": 3.3, \"tmax\": 27.2, \"tmin\": 15.6, \"rain\": 25.9}, {\"w_date\": \"2/4/82\", \"srad\": 12.5, \"tmax\": 24.4, \"tmin\": 14.4, \"rain\": 1.0}, {\"w_date\": \"2/5/82\", \"srad\": 8.5, \"tmax\": 27.8, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"2/6/82\", \"srad\": 6.3, \"tmax\": 26.7, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"2/7/82\", \"srad\": 8.0, \"tmax\": 26.7, \"tmin\": 10.6, \"rain\": 0.0}, {\"w_date\": \"2/8/82\", \"srad\": 12.5, \"tmax\": 25.0, \"tmin\": 10.0, \"rain\": 0.0}, {\"w_date\": \"2/9/82\", \"srad\": 7.9, \"tmax\": 26.7, \"tmin\": 16.1, \"rain\": 0.0}, {\"w_date\": \"2/10/82\", \"srad\": 4.0, \"tmax\": 25.6, \"tmin\": 14.4, \"rain\": 0.8}, {\"w_date\": \"2/11/82\", \"srad\": 6.5, \"tmax\": 18.9, \"tmin\": 12.8, \"rain\": 0.0}, {\"w_date\": \"2/12/82\", \"srad\": 7.4, \"tmax\": 25.0, \"tmin\": 10.6, \"rain\": 0.8}, {\"w_date\": \"2/13/82\", \"srad\": 13.3, \"tmax\": 25.6, \"tmin\": 10.6, \"rain\": 18.0}, {\"w_date\": \"2/14/82\", \"srad\": 16.1, \"tmax\": 26.1, \"tmin\": 5.6, \"rain\": 0.0}, {\"w_date\": \"2/15/82\", \"srad\": 9.6, \"tmax\": 26.7, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"2/16/82\", \"srad\": 10.8, \"tmax\": 27.8, \"tmin\": 16.1, \"rain\": 67.3}, {\"w_date\": \"2/17/82\", \"srad\": 13.4, \"tmax\": 27.2, \"tmin\": 14.4, \"rain\": 10.9}, {\"w_date\": \"2/18/82\", \"srad\": 17.0, \"tmax\": 26.1, \"tmin\": 11.1, \"rain\": 0.0}, {\"w_date\": \"2/19/82\", \"srad\": 15.5, \"tmax\": 25.6, \"tmin\": 12.2, \"rain\": 0.0}, {\"w_date\": \"2/20/82\", \"srad\": 16.9, \"tmax\": 25.6, \"tmin\": 10.0, \"rain\": 0.0}, {\"w_date\": \"2/21/82\", \"srad\": 16.6, \"tmax\": 24.4, \"tmin\": 9.4, \"rain\": 0.0}, {\"w_date\": \"2/22/82\", \"srad\": 17.0, \"tmax\": 21.1, \"tmin\": 7.8, \"rain\": 0.0}, {\"w_date\": \"2/23/82\", \"srad\": 17.7, \"tmax\": 23.9, \"tmin\": 1.1, \"rain\": 0.0}, {\"w_date\": \"2/24/82\", \"srad\": 17.5, \"tmax\": 26.7, \"tmin\": 3.3, \"rain\": 0.0}, {\"w_date\": \"2/25/82\", \"srad\": 14.8, \"tmax\": 27.2, \"tmin\": 10.6, \"rain\": 0.0}, {\"w_date\": \"2/26/82\", \"srad\": 3.4, \"tmax\": 26.7, \"tmin\": 11.1, \"rain\": 0.0}, {\"w_date\": \"2/27/82\", \"srad\": 8.9, \"tmax\": 25.6, \"tmin\": 8.9, \"rain\": 0.0}, {\"w_date\": \"2/28/82\", \"srad\": 9.3, \"tmax\": 24.4, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"3/1/82\", \"srad\": 10.9, \"tmax\": 17.8, \"tmin\": 8.3, \"rain\": 0.0}, {\"w_date\": \"3/2/82\", \"srad\": 18.4, \"tmax\": 22.8, \"tmin\": 1.7, \"rain\": 0.0}, {\"w_date\": \"3/3/82\", \"srad\": 17.6, \"tmax\": 25.0, \"tmin\": 6.1, \"rain\": 0.0}, {\"w_date\": \"3/4/82\", \"srad\": 16.2, \"tmax\": 28.3, \"tmin\": 9.4, \"rain\": 0.0}, {\"w_date\": \"3/5/82\", \"srad\": 6.3, \"tmax\": 27.8, \"tmin\": 16.1, \"rain\": 8.4}, {\"w_date\": \"3/6/82\", \"srad\": 10.3, \"tmax\": 25.0, \"tmin\": 16.1, \"rain\": 35.1}, {\"w_date\": \"3/7/82\", \"srad\": 9.0, \"tmax\": 24.4, \"tmin\": 15.0, \"rain\": 14.2}, {\"w_date\": \"3/8/82\", \"srad\": 19.8, \"tmax\": 16.7, \"tmin\": 1.1, \"rain\": 0.0}, {\"w_date\": \"3/9/82\", \"srad\": 19.6, \"tmax\": 21.7, \"tmin\": 3.9, \"rain\": 0.0}, {\"w_date\": \"3/10/82\", \"srad\": 16.1, \"tmax\": 25.6, \"tmin\": 10.6, \"rain\": 0.0}, {\"w_date\": \"3/11/82\", \"srad\": 17.7, \"tmax\": 27.8, \"tmin\": 9.4, \"rain\": 0.0}, {\"w_date\": \"3/12/82\", \"srad\": 20.0, \"tmax\": 28.9, \"tmin\": 11.1, \"rain\": 0.0}, {\"w_date\": \"3/13/82\", \"srad\": 19.0, \"tmax\": 30.6, \"tmin\": 10.0, \"rain\": 0.0}, {\"w_date\": \"3/14/82\", \"srad\": 18.4, \"tmax\": 31.7, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"3/15/82\", \"srad\": 17.5, \"tmax\": 30.0, \"tmin\": 16.7, \"rain\": 0.0}, {\"w_date\": \"3/16/82\", \"srad\": 15.9, \"tmax\": 30.0, \"tmin\": 16.1, \"rain\": 0.0}, {\"w_date\": \"3/17/82\", \"srad\": 19.5, \"tmax\": 30.6, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"3/18/82\", \"srad\": 17.4, \"tmax\": 31.7, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"3/19/82\", \"srad\": 16.7, \"tmax\": 30.6, \"tmin\": 18.3, \"rain\": 1.8}, {\"w_date\": \"3/20/82\", \"srad\": 17.4, \"tmax\": 30.6, \"tmin\": 18.9, \"rain\": 0.0}, {\"w_date\": \"3/21/82\", \"srad\": 13.6, \"tmax\": 30.0, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"3/22/82\", \"srad\": 10.0, \"tmax\": 28.3, \"tmin\": 17.2, \"rain\": 0.3}, {\"w_date\": \"3/23/82\", \"srad\": 13.2, \"tmax\": 26.1, \"tmin\": 16.1, \"rain\": 9.4}, {\"w_date\": \"3/24/82\", \"srad\": 4.3, \"tmax\": 26.1, \"tmin\": 16.1, \"rain\": 4.3}, {\"w_date\": \"3/25/82\", \"srad\": 17.0, \"tmax\": 27.8, \"tmin\": 16.1, \"rain\": 26.2}, {\"w_date\": \"3/26/82\", \"srad\": 18.8, \"tmax\": 26.7, \"tmin\": 14.4, \"rain\": 2.8}, {\"w_date\": \"3/27/82\", \"srad\": 12.9, \"tmax\": 22.2, \"tmin\": 5.6, \"rain\": 0.0}, {\"w_date\": \"3/28/82\", \"srad\": 3.8, \"tmax\": 15.6, \"tmin\": 8.9, \"rain\": 9.7}, {\"w_date\": \"3/29/82\", \"srad\": 9.9, \"tmax\": 21.1, \"tmin\": 10.0, \"rain\": 25.7}, {\"w_date\": \"3/30/82\", \"srad\": 21.5, \"tmax\": 26.1, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"3/31/82\", \"srad\": 16.1, \"tmax\": 26.7, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"4/1/82\", \"srad\": 21.0, \"tmax\": 30.6, \"tmin\": 12.8, \"rain\": 0.0}, {\"w_date\": \"4/2/82\", \"srad\": 22.1, \"tmax\": 29.4, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"4/3/82\", \"srad\": 11.4, \"tmax\": 28.9, \"tmin\": 17.8, \"rain\": 0.0}, {\"w_date\": \"4/4/82\", \"srad\": 23.8, \"tmax\": 28.3, \"tmin\": 9.4, \"rain\": 0.0}, {\"w_date\": \"4/5/82\", \"srad\": 18.9, \"tmax\": 29.4, \"tmin\": 11.1, \"rain\": 0.0}, {\"w_date\": \"4/6/82\", \"srad\": 24.1, \"tmax\": 25.6, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"4/7/82\", \"srad\": 25.1, \"tmax\": 22.2, \"tmin\": 6.7, \"rain\": 0.0}, {\"w_date\": \"4/8/82\", \"srad\": 0.8, \"tmax\": 21.1, \"tmin\": 11.1, \"rain\": 48.0}, {\"w_date\": \"4/9/82\", \"srad\": 8.8, \"tmax\": 23.3, \"tmin\": 17.2, \"rain\": 98.8}, {\"w_date\": \"4/10/82\", \"srad\": 3.8, \"tmax\": 23.9, \"tmin\": 10.6, \"rain\": 3.6}, {\"w_date\": \"4/11/82\", \"srad\": 23.0, \"tmax\": 20.6, \"tmin\": 8.9, \"rain\": 42.7}, {\"w_date\": \"4/12/82\", \"srad\": 25.4, \"tmax\": 25.0, \"tmin\": 6.1, \"rain\": 0.0}, {\"w_date\": \"4/13/82\", \"srad\": 21.6, \"tmax\": 28.3, \"tmin\": 11.7, \"rain\": 0.0}, {\"w_date\": \"4/14/82\", \"srad\": 21.6, \"tmax\": 27.2, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"4/15/82\", \"srad\": 16.6, \"tmax\": 28.3, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"4/16/82\", \"srad\": 20.7, \"tmax\": 30.6, \"tmin\": 17.8, \"rain\": 0.0}, {\"w_date\": \"4/17/82\", \"srad\": 21.0, \"tmax\": 31.1, \"tmin\": 19.4, \"rain\": 0.5}, {\"w_date\": \"4/18/82\", \"srad\": 18.9, \"tmax\": 30.0, \"tmin\": 18.9, \"rain\": 0.5}, {\"w_date\": \"4/19/82\", \"srad\": 21.6, \"tmax\": 30.0, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"4/20/82\", \"srad\": 20.7, \"tmax\": 31.1, \"tmin\": 18.9, \"rain\": 0.0}, {\"w_date\": \"4/21/82\", \"srad\": 24.3, \"tmax\": 32.2, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"4/22/82\", \"srad\": 23.7, \"tmax\": 31.7, \"tmin\": 17.8, \"rain\": 0.0}, {\"w_date\": \"4/23/82\", \"srad\": 8.6, \"tmax\": 31.1, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"4/24/82\", \"srad\": 7.5, \"tmax\": 22.2, \"tmin\": 13.9, \"rain\": 0.0}, {\"w_date\": \"4/25/82\", \"srad\": 7.7, \"tmax\": 25.6, \"tmin\": 16.7, \"rain\": 4.3}, {\"w_date\": \"4/26/82\", \"srad\": 10.7, \"tmax\": 25.6, \"tmin\": 19.4, \"rain\": 23.1}, {\"w_date\": \"4/27/82\", \"srad\": 23.4, \"tmax\": 29.4, \"tmin\": 15.6, \"rain\": 0.3}, {\"w_date\": \"4/28/82\", \"srad\": 23.7, \"tmax\": 28.9, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"4/29/82\", \"srad\": 18.5, \"tmax\": 27.2, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"4/30/82\", \"srad\": 20.7, \"tmax\": 26.7, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"5/1/82\", \"srad\": 18.2, \"tmax\": 26.7, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"5/2/82\", \"srad\": 19.9, \"tmax\": 27.8, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"5/3/82\", \"srad\": 21.8, \"tmax\": 28.9, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"5/4/82\", \"srad\": 23.6, \"tmax\": 28.9, \"tmin\": 12.8, \"rain\": 0.0}, {\"w_date\": \"5/5/82\", \"srad\": 24.4, \"tmax\": 28.3, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"5/6/82\", \"srad\": 21.7, \"tmax\": 27.8, \"tmin\": 12.2, \"rain\": 0.0}, {\"w_date\": \"5/7/82\", \"srad\": 23.6, \"tmax\": 28.9, \"tmin\": 11.7, \"rain\": 0.0}, {\"w_date\": \"5/8/82\", \"srad\": 18.2, \"tmax\": 29.4, \"tmin\": 17.8, \"rain\": 1.0}, {\"w_date\": \"5/9/82\", \"srad\": 26.3, \"tmax\": 29.4, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"5/10/82\", \"srad\": 26.3, \"tmax\": 29.4, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"5/11/82\", \"srad\": 24.9, \"tmax\": 30.0, \"tmin\": 11.1, \"rain\": 0.0}, {\"w_date\": \"5/12/82\", \"srad\": 23.5, \"tmax\": 31.1, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"5/13/82\", \"srad\": 24.2, \"tmax\": 30.6, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"5/14/82\", \"srad\": 26.7, \"tmax\": 31.1, \"tmin\": 12.8, \"rain\": 0.0}, {\"w_date\": \"5/15/82\", \"srad\": 23.9, \"tmax\": 32.2, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"5/16/82\", \"srad\": 22.8, \"tmax\": 33.3, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"5/17/82\", \"srad\": 22.7, \"tmax\": 32.8, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"5/18/82\", \"srad\": 21.5, \"tmax\": 31.1, \"tmin\": 16.7, \"rain\": 0.0}, {\"w_date\": \"5/19/82\", \"srad\": 22.4, \"tmax\": 30.6, \"tmin\": 16.7, \"rain\": 0.0}, {\"w_date\": \"5/20/82\", \"srad\": 22.5, \"tmax\": 31.7, \"tmin\": 16.1, \"rain\": 0.0}, {\"w_date\": \"5/21/82\", \"srad\": 22.6, \"tmax\": 32.2, \"tmin\": 16.1, \"rain\": 0.0}, {\"w_date\": \"5/22/82\", \"srad\": 17.4, \"tmax\": 32.8, \"tmin\": 16.7, \"rain\": 2.0}, {\"w_date\": \"5/23/82\", \"srad\": 13.2, \"tmax\": 33.3, \"tmin\": 20.0, \"rain\": 35.1}, {\"w_date\": \"5/24/82\", \"srad\": 21.5, \"tmax\": 32.8, \"tmin\": 18.9, \"rain\": 3.6}, {\"w_date\": \"5/25/82\", \"srad\": 20.3, \"tmax\": 32.8, \"tmin\": 20.0, \"rain\": 2.5}, {\"w_date\": \"5/26/82\", \"srad\": 15.0, \"tmax\": 31.7, \"tmin\": 20.6, \"rain\": 1.0}, {\"w_date\": \"5/27/82\", \"srad\": 18.2, \"tmax\": 32.2, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"5/28/82\", \"srad\": 22.1, \"tmax\": 33.3, \"tmin\": 20.6, \"rain\": 4.3}, {\"w_date\": \"5/29/82\", \"srad\": 19.3, \"tmax\": 34.4, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"5/30/82\", \"srad\": 7.0, \"tmax\": 32.2, \"tmin\": 22.8, \"rain\": 5.6}, {\"w_date\": \"5/31/82\", \"srad\": 11.8, \"tmax\": 30.0, \"tmin\": 20.6, \"rain\": 23.9}, {\"w_date\": \"6/1/82\", \"srad\": 20.0, \"tmax\": 31.1, \"tmin\": 21.1, \"rain\": 15.2}, {\"w_date\": \"6/2/82\", \"srad\": 24.9, \"tmax\": 33.3, \"tmin\": 20.0, \"rain\": 0.0}, {\"w_date\": \"6/3/82\", \"srad\": 15.8, \"tmax\": 33.3, \"tmin\": 22.8, \"rain\": 1.8}, {\"w_date\": \"6/4/82\", \"srad\": 21.6, \"tmax\": 33.3, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"6/5/82\", \"srad\": 23.8, \"tmax\": 33.9, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"6/6/82\", \"srad\": 26.5, \"tmax\": 34.4, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"6/7/82\", \"srad\": 26.9, \"tmax\": 33.9, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"6/8/82\", \"srad\": 26.3, \"tmax\": 34.4, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"6/9/82\", \"srad\": 24.4, \"tmax\": 34.4, \"tmin\": 19.4, \"rain\": 0.0}, {\"w_date\": \"6/10/82\", \"srad\": 25.5, \"tmax\": 34.4, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"6/11/82\", \"srad\": 23.6, \"tmax\": 35.0, \"tmin\": 19.4, \"rain\": 0.0}, {\"w_date\": \"6/12/82\", \"srad\": 15.2, \"tmax\": 31.1, \"tmin\": 20.0, \"rain\": 50.8}, {\"w_date\": \"6/13/82\", \"srad\": 21.1, \"tmax\": 33.3, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"6/14/82\", \"srad\": 25.5, \"tmax\": 33.3, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"6/15/82\", \"srad\": 23.2, \"tmax\": 33.9, \"tmin\": 20.6, \"rain\": 0.0}, {\"w_date\": \"6/16/82\", \"srad\": 25.5, \"tmax\": 33.9, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"6/17/82\", \"srad\": 6.0, \"tmax\": 31.1, \"tmin\": 21.1, \"rain\": 8.1}, {\"w_date\": \"6/18/82\", \"srad\": 13.5, \"tmax\": 27.8, \"tmin\": 21.1, \"rain\": 70.1}, {\"w_date\": \"6/19/82\", \"srad\": 24.3, \"tmax\": 33.3, \"tmin\": 20.0, \"rain\": 0.0}, {\"w_date\": \"6/20/82\", \"srad\": 25.9, \"tmax\": 33.9, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"6/21/82\", \"srad\": 20.9, \"tmax\": 32.8, \"tmin\": 20.0, \"rain\": 3.3}, {\"w_date\": \"6/22/82\", \"srad\": 18.5, \"tmax\": 28.3, \"tmin\": 23.3, \"rain\": 2.0}, {\"w_date\": \"6/23/82\", \"srad\": 11.7, \"tmax\": 27.8, \"tmin\": 21.7, \"rain\": 26.9}, {\"w_date\": \"6/24/82\", \"srad\": 18.6, \"tmax\": 30.6, \"tmin\": 22.2, \"rain\": 6.4}, {\"w_date\": \"6/25/82\", \"srad\": 22.9, \"tmax\": 32.2, \"tmin\": 19.4, \"rain\": 10.9}, {\"w_date\": \"6/26/82\", \"srad\": 10.5, \"tmax\": 31.7, \"tmin\": 21.7, \"rain\": 23.4}, {\"w_date\": \"6/27/82\", \"srad\": 24.6, \"tmax\": 32.2, \"tmin\": 21.1, \"rain\": 1.8}, {\"w_date\": \"6/28/82\", \"srad\": 24.6, \"tmax\": 32.8, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"6/29/82\", \"srad\": 24.8, \"tmax\": 33.3, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"6/30/82\", \"srad\": 16.0, \"tmax\": 32.8, \"tmin\": 22.8, \"rain\": 1.3}, {\"w_date\": \"7/1/82\", \"srad\": 23.7, \"tmax\": 33.9, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"7/2/82\", \"srad\": 18.7, \"tmax\": 33.9, \"tmin\": 23.9, \"rain\": 0.0}, {\"w_date\": \"7/3/82\", \"srad\": 24.6, \"tmax\": 35.0, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"7/4/82\", \"srad\": 21.1, \"tmax\": 35.0, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"7/5/82\", \"srad\": 23.7, \"tmax\": 34.4, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"7/6/82\", \"srad\": 19.0, \"tmax\": 32.2, \"tmin\": 20.6, \"rain\": 4.1}, {\"w_date\": \"7/7/82\", \"srad\": 16.0, \"tmax\": 31.7, \"tmin\": 21.1, \"rain\": 4.8}, {\"w_date\": \"7/8/82\", \"srad\": 19.8, \"tmax\": 32.2, \"tmin\": 21.1, \"rain\": 1.3}, {\"w_date\": \"7/9/82\", \"srad\": 13.7, \"tmax\": 32.2, \"tmin\": 22.2, \"rain\": 7.1}, {\"w_date\": \"7/10/82\", \"srad\": 17.4, \"tmax\": 32.8, \"tmin\": 21.7, \"rain\": 1.3}, {\"w_date\": \"7/11/82\", \"srad\": 18.3, \"tmax\": 32.8, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"7/12/82\", \"srad\": 21.0, \"tmax\": 32.8, \"tmin\": 21.1, \"rain\": 2.3}, {\"w_date\": \"7/13/82\", \"srad\": 23.2, \"tmax\": 33.9, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"7/14/82\", \"srad\": 25.4, \"tmax\": 34.4, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"7/15/82\", \"srad\": 18.3, \"tmax\": 33.9, \"tmin\": 23.3, \"rain\": 55.9}, {\"w_date\": \"7/16/82\", \"srad\": 14.9, \"tmax\": 32.8, \"tmin\": 22.8, \"rain\": 1.0}, {\"w_date\": \"7/17/82\", \"srad\": 13.6, \"tmax\": 31.1, \"tmin\": 22.8, \"rain\": 0.0}, {\"w_date\": \"7/18/82\", \"srad\": 14.3, \"tmax\": 32.2, \"tmin\": 22.8, \"rain\": 2.5}, {\"w_date\": \"7/19/82\", \"srad\": 17.0, \"tmax\": 32.2, \"tmin\": 22.2, \"rain\": 30.0}, {\"w_date\": \"7/20/82\", \"srad\": 10.9, \"tmax\": 31.1, \"tmin\": 21.7, \"rain\": 20.8}, {\"w_date\": \"7/21/82\", \"srad\": 13.2, \"tmax\": 31.1, \"tmin\": 21.1, \"rain\": 1.5}, {\"w_date\": \"7/22/82\", \"srad\": 17.9, \"tmax\": 31.1, \"tmin\": 22.2, \"rain\": 0.3}, {\"w_date\": \"7/23/82\", \"srad\": 16.1, \"tmax\": 32.8, \"tmin\": 22.8, \"rain\": 16.0}, {\"w_date\": \"7/24/82\", \"srad\": 18.4, \"tmax\": 32.2, \"tmin\": 21.7, \"rain\": 4.1}, {\"w_date\": \"7/25/82\", \"srad\": 19.7, \"tmax\": 33.3, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"7/26/82\", \"srad\": 19.4, \"tmax\": 33.9, \"tmin\": 22.2, \"rain\": 0.8}, {\"w_date\": \"7/27/82\", \"srad\": 5.2, \"tmax\": 34.4, \"tmin\": 23.3, \"rain\": 10.7}, {\"w_date\": \"7/28/82\", \"srad\": 9.5, \"tmax\": 27.8, \"tmin\": 22.8, \"rain\": 7.4}, {\"w_date\": \"7/29/82\", \"srad\": 14.9, \"tmax\": 31.7, \"tmin\": 22.8, \"rain\": 0.0}, {\"w_date\": \"7/30/82\", \"srad\": 19.4, \"tmax\": 32.8, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"7/31/82\", \"srad\": 20.1, \"tmax\": 33.9, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"8/1/82\", \"srad\": 23.5, \"tmax\": 33.9, \"tmin\": 23.3, \"rain\": 0.0}, {\"w_date\": \"8/2/82\", \"srad\": 9.6, \"tmax\": 32.8, \"tmin\": 22.8, \"rain\": 13.0}, {\"w_date\": \"8/3/82\", \"srad\": 22.5, \"tmax\": 32.2, \"tmin\": 22.8, \"rain\": 20.1}, {\"w_date\": \"8/4/82\", \"srad\": 21.0, \"tmax\": 32.2, \"tmin\": 20.6, \"rain\": 0.0}, {\"w_date\": \"8/5/82\", \"srad\": 14.7, \"tmax\": 32.2, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"8/6/82\", \"srad\": 18.5, \"tmax\": 32.2, \"tmin\": 21.1, \"rain\": 4.1}, {\"w_date\": \"8/7/82\", \"srad\": 23.0, \"tmax\": 33.9, \"tmin\": 21.7, \"rain\": 0.3}, {\"w_date\": \"8/8/82\", \"srad\": 21.0, \"tmax\": 33.9, \"tmin\": 26.1, \"rain\": 0.0}, {\"w_date\": \"8/9/82\", \"srad\": 17.1, \"tmax\": 32.8, \"tmin\": 22.8, \"rain\": 1.3}, {\"w_date\": \"8/10/82\", \"srad\": 16.6, \"tmax\": 33.3, \"tmin\": 23.3, \"rain\": 0.0}, {\"w_date\": \"8/11/82\", \"srad\": 25.6, \"tmax\": 33.3, \"tmin\": 18.9, \"rain\": 0.0}, {\"w_date\": \"8/12/82\", \"srad\": 19.0, \"tmax\": 33.3, \"tmin\": 20.6, \"rain\": 0.3}, {\"w_date\": \"8/13/82\", \"srad\": 16.1, \"tmax\": 32.2, \"tmin\": 20.6, \"rain\": 0.8}, {\"w_date\": \"8/14/82\", \"srad\": 16.4, \"tmax\": 31.7, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"8/15/82\", \"srad\": 20.3, \"tmax\": 35.0, \"tmin\": 20.6, \"rain\": 0.8}, {\"w_date\": \"8/16/82\", \"srad\": 20.3, \"tmax\": 33.9, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"8/17/82\", \"srad\": 19.0, \"tmax\": 32.2, \"tmin\": 22.2, \"rain\": 13.7}, {\"w_date\": \"8/18/82\", \"srad\": 16.6, \"tmax\": 26.7, \"tmin\": 20.6, \"rain\": 3.3}, {\"w_date\": \"8/19/82\", \"srad\": 7.9, \"tmax\": 28.3, \"tmin\": 21.7, \"rain\": 20.3}, {\"w_date\": \"8/20/82\", \"srad\": 20.4, \"tmax\": 32.2, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"8/21/82\", \"srad\": 11.8, \"tmax\": 32.2, \"tmin\": 21.7, \"rain\": 5.1}, {\"w_date\": \"8/22/82\", \"srad\": 16.6, \"tmax\": 32.2, \"tmin\": 22.2, \"rain\": 1.3}, {\"w_date\": \"8/23/82\", \"srad\": 15.2, \"tmax\": 31.7, \"tmin\": 22.2, \"rain\": 64.5}, {\"w_date\": \"8/24/82\", \"srad\": 18.9, \"tmax\": 33.3, \"tmin\": 22.8, \"rain\": 1.3}, {\"w_date\": \"8/25/82\", \"srad\": 18.8, \"tmax\": 33.3, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"8/26/82\", \"srad\": 21.4, \"tmax\": 35.6, \"tmin\": 23.9, \"rain\": 0.0}, {\"w_date\": \"8/27/82\", \"srad\": 11.0, \"tmax\": 35.6, \"tmin\": 22.8, \"rain\": 3.6}, {\"w_date\": \"8/28/82\", \"srad\": 18.6, \"tmax\": 35.0, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"8/29/82\", \"srad\": 15.8, \"tmax\": 35.0, \"tmin\": 22.2, \"rain\": 3.6}, {\"w_date\": \"8/30/82\", \"srad\": 22.5, \"tmax\": 31.1, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"8/31/82\", \"srad\": 21.1, \"tmax\": 32.2, \"tmin\": 20.6, \"rain\": 0.0}, {\"w_date\": \"9/1/82\", \"srad\": 13.2, \"tmax\": 32.2, \"tmin\": 20.6, \"rain\": 0.0}, {\"w_date\": \"9/2/82\", \"srad\": 15.6, \"tmax\": 33.3, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"9/3/82\", \"srad\": 16.9, \"tmax\": 34.4, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"9/4/82\", \"srad\": 17.0, \"tmax\": 34.4, \"tmin\": 22.2, \"rain\": 0.0}, {\"w_date\": \"9/5/82\", \"srad\": 12.2, \"tmax\": 33.9, \"tmin\": 21.7, \"rain\": 11.9}, {\"w_date\": \"9/6/82\", \"srad\": 9.0, \"tmax\": 34.4, \"tmin\": 21.1, \"rain\": 2.0}, {\"w_date\": \"9/7/82\", \"srad\": 11.1, \"tmax\": 30.0, \"tmin\": 20.0, \"rain\": 0.0}, {\"w_date\": \"9/8/82\", \"srad\": 9.6, \"tmax\": 30.6, \"tmin\": 20.6, \"rain\": 19.0}, {\"w_date\": \"9/9/82\", \"srad\": 7.1, \"tmax\": 30.0, \"tmin\": 21.1, \"rain\": 39.9}, {\"w_date\": \"9/10/82\", \"srad\": 5.5, \"tmax\": 30.0, \"tmin\": 21.1, \"rain\": 24.1}, {\"w_date\": \"9/11/82\", \"srad\": 13.4, \"tmax\": 32.8, \"tmin\": 21.7, \"rain\": 7.6}, {\"w_date\": \"9/12/82\", \"srad\": 14.6, \"tmax\": 33.3, \"tmin\": 22.8, \"rain\": 0.0}, {\"w_date\": \"9/13/82\", \"srad\": 14.5, \"tmax\": 33.3, \"tmin\": 22.8, \"rain\": 2.5}, {\"w_date\": \"9/14/82\", \"srad\": 12.3, \"tmax\": 32.8, \"tmin\": 22.2, \"rain\": 1.8}, {\"w_date\": \"9/15/82\", \"srad\": 13.0, \"tmax\": 32.2, \"tmin\": 19.4, \"rain\": 0.0}, {\"w_date\": \"9/16/82\", \"srad\": 12.6, \"tmax\": 32.8, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"9/17/82\", \"srad\": 13.9, \"tmax\": 32.8, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"9/18/82\", \"srad\": 12.2, \"tmax\": 32.8, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"9/19/82\", \"srad\": 12.2, \"tmax\": 32.8, \"tmin\": 21.1, \"rain\": 15.7}, {\"w_date\": \"9/20/82\", \"srad\": 11.1, \"tmax\": 31.7, \"tmin\": 21.1, \"rain\": 7.4}, {\"w_date\": \"9/21/82\", \"srad\": 5.2, \"tmax\": 29.4, \"tmin\": 21.1, \"rain\": 12.7}, {\"w_date\": \"9/22/82\", \"srad\": 11.4, \"tmax\": 27.8, \"tmin\": 19.4, \"rain\": 0.0}, {\"w_date\": \"9/23/82\", \"srad\": 8.9, \"tmax\": 27.2, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"9/24/82\", \"srad\": 11.2, \"tmax\": 30.0, \"tmin\": 17.8, \"rain\": 0.0}, {\"w_date\": \"9/25/82\", \"srad\": 5.0, \"tmax\": 28.9, \"tmin\": 20.0, \"rain\": 8.9}, {\"w_date\": \"9/26/82\", \"srad\": 7.6, \"tmax\": 25.0, \"tmin\": 16.7, \"rain\": 24.1}, {\"w_date\": \"9/27/82\", \"srad\": 15.6, \"tmax\": 28.9, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"9/28/82\", \"srad\": 13.9, \"tmax\": 29.4, \"tmin\": 16.7, \"rain\": 0.0}, {\"w_date\": \"9/29/82\", \"srad\": 12.1, \"tmax\": 29.4, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"9/30/82\", \"srad\": 6.7, \"tmax\": 28.9, \"tmin\": 21.1, \"rain\": 2.5}, {\"w_date\": \"10/1/82\", \"srad\": 8.0, \"tmax\": 26.7, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"10/2/82\", \"srad\": 10.0, \"tmax\": 28.3, \"tmin\": 19.4, \"rain\": 0.0}, {\"w_date\": \"10/3/82\", \"srad\": 19.0, \"tmax\": 30.0, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"10/4/82\", \"srad\": 16.0, \"tmax\": 31.7, \"tmin\": 20.6, \"rain\": 0.0}, {\"w_date\": \"10/5/82\", \"srad\": 9.0, \"tmax\": 31.1, \"tmin\": 22.8, \"rain\": 0.0}, {\"w_date\": \"10/6/82\", \"srad\": 16.0, \"tmax\": 31.1, \"tmin\": 21.7, \"rain\": 13.0}, {\"w_date\": \"10/7/82\", \"srad\": 13.0, \"tmax\": 30.0, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"10/8/82\", \"srad\": 17.0, \"tmax\": 31.7, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"10/9/82\", \"srad\": 15.0, \"tmax\": 31.7, \"tmin\": 18.9, \"rain\": 0.0}, {\"w_date\": \"10/10/82\", \"srad\": 15.0, \"tmax\": 32.8, \"tmin\": 20.0, \"rain\": 0.0}, {\"w_date\": \"10/11/82\", \"srad\": 13.0, \"tmax\": 32.2, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"10/12/82\", \"srad\": 11.0, \"tmax\": 29.4, \"tmin\": 21.7, \"rain\": 0.0}, {\"w_date\": \"10/13/82\", \"srad\": 14.0, \"tmax\": 30.6, \"tmin\": 18.9, \"rain\": 0.0}, {\"w_date\": \"10/14/82\", \"srad\": 10.0, \"tmax\": 28.9, \"tmin\": 16.7, \"rain\": 0.0}, {\"w_date\": \"10/15/82\", \"srad\": 15.0, \"tmax\": 27.8, \"tmin\": 11.7, \"rain\": 0.0}, {\"w_date\": \"10/16/82\", \"srad\": 9.0, \"tmax\": 26.1, \"tmin\": 10.0, \"rain\": 0.0}, {\"w_date\": \"10/17/82\", \"srad\": 14.0, \"tmax\": 26.7, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"10/18/82\", \"srad\": 6.0, \"tmax\": 25.6, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"10/19/82\", \"srad\": 14.0, \"tmax\": 28.3, \"tmin\": 16.1, \"rain\": 0.0}, {\"w_date\": \"10/20/82\", \"srad\": 13.0, \"tmax\": 30.0, \"tmin\": 16.7, \"rain\": 0.0}, {\"w_date\": \"10/21/82\", \"srad\": 14.0, \"tmax\": 30.0, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"10/22/82\", \"srad\": 11.0, \"tmax\": 29.4, \"tmin\": 16.1, \"rain\": 0.0}, {\"w_date\": \"10/23/82\", \"srad\": 2.0, \"tmax\": 27.8, \"tmin\": 14.4, \"rain\": 16.5}, {\"w_date\": \"10/24/82\", \"srad\": 14.0, \"tmax\": 18.9, \"tmin\": 8.9, \"rain\": 1.3}, {\"w_date\": \"10/25/82\", \"srad\": 16.0, \"tmax\": 20.6, \"tmin\": 5.6, \"rain\": 0.0}, {\"w_date\": \"10/26/82\", \"srad\": 15.0, \"tmax\": 22.8, \"tmin\": 6.1, \"rain\": 0.0}, {\"w_date\": \"10/27/82\", \"srad\": 15.0, \"tmax\": 23.9, \"tmin\": 7.2, \"rain\": 0.0}, {\"w_date\": \"10/28/82\", \"srad\": 14.0, \"tmax\": 25.6, \"tmin\": 11.7, \"rain\": 0.0}, {\"w_date\": \"10/29/82\", \"srad\": 14.0, \"tmax\": 27.8, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"10/30/82\", \"srad\": 9.0, \"tmax\": 27.8, \"tmin\": 16.1, \"rain\": 0.0}, {\"w_date\": \"10/31/82\", \"srad\": 7.0, \"tmax\": 27.8, \"tmin\": 18.9, \"rain\": 0.0}, {\"w_date\": \"11/1/82\", \"srad\": 11.0, \"tmax\": 29.4, \"tmin\": 20.0, \"rain\": 0.3}, {\"w_date\": \"11/2/82\", \"srad\": 9.0, \"tmax\": 29.4, \"tmin\": 20.6, \"rain\": 0.0}, {\"w_date\": \"11/3/82\", \"srad\": 9.0, \"tmax\": 28.9, \"tmin\": 19.4, \"rain\": 21.6}, {\"w_date\": \"11/4/82\", \"srad\": 9.0, \"tmax\": 26.7, \"tmin\": 17.8, \"rain\": 16.5}, {\"w_date\": \"11/5/82\", \"srad\": 10.0, \"tmax\": 18.3, \"tmin\": 7.2, \"rain\": 0.0}, {\"w_date\": \"11/6/82\", \"srad\": 12.0, \"tmax\": 18.9, \"tmin\": 2.2, \"rain\": 0.0}, {\"w_date\": \"11/7/82\", \"srad\": 12.0, \"tmax\": 21.1, \"tmin\": 7.8, \"rain\": 0.0}, {\"w_date\": \"11/8/82\", \"srad\": 13.0, \"tmax\": 24.4, \"tmin\": 12.8, \"rain\": 0.0}, {\"w_date\": \"11/9/82\", \"srad\": 11.0, \"tmax\": 25.0, \"tmin\": 12.8, \"rain\": 0.0}, {\"w_date\": \"11/10/82\", \"srad\": 12.0, \"tmax\": 25.6, \"tmin\": 10.6, \"rain\": 0.0}, {\"w_date\": \"11/11/82\", \"srad\": 8.0, \"tmax\": 26.7, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"11/12/82\", \"srad\": 7.0, \"tmax\": 27.8, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"11/13/82\", \"srad\": 11.0, \"tmax\": 31.1, \"tmin\": 13.3, \"rain\": 3.6}, {\"w_date\": \"11/14/82\", \"srad\": 12.0, \"tmax\": 25.0, \"tmin\": 6.7, \"rain\": 0.0}, {\"w_date\": \"11/15/82\", \"srad\": 10.0, \"tmax\": 23.9, \"tmin\": 15.0, \"rain\": 0.0}, {\"w_date\": \"11/16/82\", \"srad\": 11.0, \"tmax\": 23.9, \"tmin\": 9.4, \"rain\": 0.0}, {\"w_date\": \"11/17/82\", \"srad\": 13.0, \"tmax\": 26.7, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"11/18/82\", \"srad\": 13.0, \"tmax\": 28.3, \"tmin\": 17.8, \"rain\": 0.0}, {\"w_date\": \"11/19/82\", \"srad\": 11.0, \"tmax\": 26.7, \"tmin\": 17.8, \"rain\": 0.0}, {\"w_date\": \"11/20/82\", \"srad\": 12.0, \"tmax\": 25.6, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"11/21/82\", \"srad\": 14.0, \"tmax\": 27.2, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"11/22/82\", \"srad\": 13.0, \"tmax\": 26.1, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"11/23/82\", \"srad\": 13.0, \"tmax\": 27.2, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"11/24/82\", \"srad\": 13.0, \"tmax\": 27.8, \"tmin\": 12.2, \"rain\": 0.0}, {\"w_date\": \"11/25/82\", \"srad\": 15.0, \"tmax\": 27.2, \"tmin\": 12.8, \"rain\": 0.0}, {\"w_date\": \"11/26/82\", \"srad\": 12.0, \"tmax\": 26.7, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"11/27/82\", \"srad\": 3.0, \"tmax\": 27.8, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"11/28/82\", \"srad\": 10.0, \"tmax\": 28.9, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"11/29/82\", \"srad\": 10.0, \"tmax\": 27.2, \"tmin\": 18.9, \"rain\": 0.0}, {\"w_date\": \"11/30/82\", \"srad\": 9.0, \"tmax\": 29.4, \"tmin\": 19.4, \"rain\": 0.0}, {\"w_date\": \"12/1/82\", \"srad\": 8.0, \"tmax\": 28.9, \"tmin\": 20.6, \"rain\": 0.0}, {\"w_date\": \"12/2/82\", \"srad\": 10.0, \"tmax\": 29.4, \"tmin\": 21.1, \"rain\": 0.0}, {\"w_date\": \"12/3/82\", \"srad\": 11.0, \"tmax\": 29.4, \"tmin\": 18.9, \"rain\": 0.0}, {\"w_date\": \"12/4/82\", \"srad\": 11.0, \"tmax\": 28.9, \"tmin\": 18.3, \"rain\": 0.0}, {\"w_date\": \"12/5/82\", \"srad\": 8.0, \"tmax\": 28.9, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"12/6/82\", \"srad\": 2.0, \"tmax\": 27.2, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"12/7/82\", \"srad\": 5.0, \"tmax\": 21.7, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"12/8/82\", \"srad\": 2.0, \"tmax\": 20.0, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"12/9/82\", \"srad\": 7.0, \"tmax\": 23.3, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"12/10/82\", \"srad\": 2.0, \"tmax\": 22.2, \"tmin\": 15.6, \"rain\": 3.3}, {\"w_date\": \"12/11/82\", \"srad\": 5.0, \"tmax\": 26.1, \"tmin\": 17.2, \"rain\": 0.0}, {\"w_date\": \"12/12/82\", \"srad\": 2.0, \"tmax\": 23.9, \"tmin\": 10.0, \"rain\": 19.0}, {\"w_date\": \"12/13/82\", \"srad\": 11.0, \"tmax\": 12.8, \"tmin\": 2.8, \"rain\": 0.0}, {\"w_date\": \"12/14/82\", \"srad\": 6.0, \"tmax\": 18.9, \"tmin\": 3.3, \"rain\": 0.0}, {\"w_date\": \"12/15/82\", \"srad\": 8.0, \"tmax\": 25.0, \"tmin\": 12.8, \"rain\": 0.0}, {\"w_date\": \"12/16/82\", \"srad\": 6.0, \"tmax\": 23.9, \"tmin\": 15.6, \"rain\": 13.7}, {\"w_date\": \"12/17/82\", \"srad\": 11.0, \"tmax\": 20.0, \"tmin\": 2.8, \"rain\": 0.0}, {\"w_date\": \"12/18/82\", \"srad\": 11.0, \"tmax\": 15.6, \"tmin\": -1.1, \"rain\": 0.0}, {\"w_date\": \"12/19/82\", \"srad\": 9.0, \"tmax\": 20.0, \"tmin\": -1.1, \"rain\": 0.0}, {\"w_date\": \"12/20/82\", \"srad\": 11.0, \"tmax\": 18.9, \"tmin\": 1.1, \"rain\": 0.0}, {\"w_date\": \"12/21/82\", \"srad\": 11.0, \"tmax\": 19.4, \"tmin\": 0.6, \"rain\": 0.0}, {\"w_date\": \"12/22/82\", \"srad\": 11.0, \"tmax\": 19.4, \"tmin\": 0.6, \"rain\": 0.0}, {\"w_date\": \"12/23/82\", \"srad\": 9.0, \"tmax\": 23.3, \"tmin\": 4.4, \"rain\": 0.0}, {\"w_date\": \"12/24/82\", \"srad\": 9.0, \"tmax\": 26.7, \"tmin\": 13.3, \"rain\": 0.0}, {\"w_date\": \"12/25/82\", \"srad\": 11.0, \"tmax\": 27.2, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"12/26/82\", \"srad\": 10.0, \"tmax\": 27.2, \"tmin\": 15.6, \"rain\": 0.0}, {\"w_date\": \"12/27/82\", \"srad\": 6.0, \"tmax\": 25.0, \"tmin\": 16.7, \"rain\": 0.0}, {\"w_date\": \"12/28/82\", \"srad\": 8.0, \"tmax\": 26.7, \"tmin\": 16.1, \"rain\": 0.0}, {\"w_date\": \"12/29/82\", \"srad\": 7.0, \"tmax\": 26.7, \"tmin\": 14.4, \"rain\": 0.0}, {\"w_date\": \"12/30/82\", \"srad\": 2.0, \"tmax\": 23.3, \"tmin\": 13.9, \"rain\": 0.5}, {\"w_date\": \"12/31/82\", \"srad\": 2.0, \"tmax\": 16.7, \"tmin\": 12.8, \"rain\": 0.8}], \"tamp\": 7.5}, \"soil\": {\"soil_id\": \"IBMZ910014\", \"sl_system\": \"SCS\", \"soillong\": -82.37, \"name\": \"Millhopper Fine Sand\", \"SoilLayer\": [{\"silt\": 11.8, \"sldul\": 0.086, \"slll\": 0.02, \"clay\": 0.9, \"slbdm\": 7.4, \"slcf\": 2.0, \"sloc\": 5.3, \"slphw\": 1.36, \"slsat\": 0.23, \"sllb\": 5.0}, {\"silt\": 11.8, \"sldul\": 0.086, \"slll\": 0.023, \"clay\": 0.9, \"slbdm\": 7.4, \"slcf\": 1.0, \"sloc\": 5.4, \"slphw\": 1.4, \"slsat\": 0.23, \"sllb\": 15.0}, {\"silt\": 6.4, \"sldul\": 0.086, \"slll\": 0.023, \"clay\": 4.6, \"slbdm\": 15.8, \"slcf\": 1.0, \"sloc\": 5.7, \"slphw\": 1.46, \"slsat\": 0.23, \"sllb\": 30.0}, {\"silt\": 5.4, \"sldul\": 0.086, \"slll\": 0.023, \"clay\": 5.8, \"slbdm\": 28.0, \"slcf\": 0.5, \"sloc\": 5.8, \"slphw\": 1.46, \"slsat\": 0.23, \"sllb\": 45.0}, {\"silt\": 5.4, \"sldul\": 0.086, \"slll\": 0.023, \"clay\": 5.8, \"slbdm\": 28.0, \"slcf\": 0.5, \"sloc\": 5.8, \"slphw\": 1.47, \"slsat\": 0.23, \"sllb\": 60.0}, {\"silt\": 4.2, \"sldul\": 0.076, \"slll\": 0.021, \"clay\": 9.6, \"slbdm\": 27.6, \"slcf\": 0.1, \"sloc\": 5.9, \"slphw\": 1.43, \"slsat\": 0.23, \"sllb\": 90.0}, {\"silt\": 4.2, \"sldul\": 0.076, \"slll\": 0.02, \"clay\": 9.6, \"slbdm\": 17.5, \"slcf\": 0.1, \"sloc\": 5.9, \"slphw\": 1.48, \"slsat\": 0.23, \"sllb\": 120.0}, {\"silt\": 3.6, \"sldul\": 0.13, \"slll\": 0.027, \"clay\": 8.3, \"slbdm\": 0.3, \"slcf\": 0.04, \"sloc\": 5.9, \"slphw\": 1.57, \"slsat\": 0.23, \"sllb\": 150.0}, {\"silt\": 3.6, \"sldul\": 0.258, \"slll\": 0.07, \"clay\": 8.3, \"slbdm\": 0.1, \"slcf\": 0.24, \"sloc\": 5.9, \"slphw\": 1.79, \"slsat\": 0.36, \"sllb\": 180.0}, {\"silt\": \"\", \"sldul\": \"\", \"slll\": \"\", \"clay\": \"\", \"slbdm\": \"\", \"slcf\": \"\", \"sloc\": \"\", \"slphw\": \"\", \"slsat\": \"\", \"sllb\": \"\"}], \"sldr\": 0.65, \"salb\": 0.18, \"slnf\": 1.0, \"soillat\": 29.63, \"classifcation\": \"LS\"}}";
        ArrayList<LinkedHashMap> result;
        String filePath = "src\\test\\java\\org\\agmip\\translators\\dssat\\";
        String[] fileNames = {
            //            "UFGA8201.ZIP",
            //            "UFGA9602.TMX",
            //            "UFGA7801_SBX.ZIP",
            //            "UFGA7802_SBX.ZIP",
                        "UFGA8201_MZX.ZIP",
            //            "UFGA8202_MZX.ZIP",
//            "Anantapur-peanut-data\\APAN8601_PNX.zip",
//            "Anantapur-peanut-data\\APAN8701_PNX.zip",
//            "Anantapur-peanut-data\\APAN8803_PNX.zip",
//            "Anantapur-peanut-data\\APAN8904_PNX.zip",
//            "Anantapur-peanut-data\\APAN9001_PNX.zip",
//            "Anantapur-peanut-data\\APAN9304_PNX.zip",
//            "ksas8101_whx.zip",
//            "MACH0001.zip",
//            "MACH0002.zip",
//            "MACH0003.zip",
//            "MACH0004.zip",
        };

        File f = new File("output.txt");
        BufferedOutputStream bo;
        for (int i = 0; i < fileNames.length; i++) {
            result = obDssatControllerInput.readFiles(filePath + fileNames[i]);
            System.out.println(fileNames[i] + ":\r\n" + JSONAdapter.toJSON(result));
//            if (fileNames[i].equals(fileNames[0])) {
//                resultForWrite = result;
//            }

            // Output json for reading
            if (i != 0) {
                bo = new BufferedOutputStream(new FileOutputStream(f, true));
            } else {
                bo = new BufferedOutputStream(new FileOutputStream(f));
            }
            bo.write((fileNames[i] + ":\r\n").getBytes());
            bo.write(JSONAdapter.toJSON(result).getBytes());
            bo.write("\r\n\r\n=================================================================\r\n\r\n".getBytes());
            bo.close();
            
            obDssatControllerOutput = new DssatControllerOutput();
            obDssatControllerOutput.writeFiles("", result.get(0));
            File file = obDssatControllerOutput.getOutputZipFile();
            if (file != null) {
                assertTrue(file.exists());
                assertTrue(file.delete());
            }
        }
        f.delete();

    }
}
