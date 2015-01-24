package com.ecomm.pl4sms.composer.za.co.ecomm.pl4.kannel;


import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Helper {

    private static Logger LOG = Logger.getLogger(Helper.class);

    private static Properties props;

    public static Properties getProps() {

        if (props == null ) {
            props = new Properties();
            FileInputStream in = null;
            try {


                in = new FileInputStream("/pl4sms.properties");

                props.load(in);


            } catch (FileNotFoundException e) {
                LOG.error(e.getMessage(), e);
            } catch (IOException e) {

                LOG.error(e.getMessage(), e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {

                        LOG.error(e.getMessage(), e);
                    }
                }
            }
        }
        return props;
    }


}