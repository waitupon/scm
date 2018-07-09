package com.utils;

import com.baiwang.bop.respose.entity.bizinfo.CompanySearchResponse;
import com.baiwang.bop.respose.entity.bizinfo.CompanySearchResult;

import java.io.*;
import java.util.List;

public class BwCompanyBatch {


    public static void main(String[] args) {
        BufferedReader bis = null;
        BufferedWriter bops = null;
        try {
            File file = new File("/Users/wenhao/note/company1.txt");
            bis = new BufferedReader(new FileReader(file));
            File outfile = new File("/Users/wenhao/note/company1.csv");
            bops = new BufferedWriter(new FileWriter(outfile));
            String companyName = null;
            while((companyName = bis.readLine())!=null){
                CompanySearchResponse response = BwUtils.companySearch(companyName);
                List<CompanySearchResult> result = response.getResult();
                if(result!=null && result.size()>0){
                    for(CompanySearchResult c : result){
                        String str = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                                                                c.getName(),c.getTaxId(),
                                                                c.getAddressAndPhone(),c.getBankAndAccount(),
                                c.getBank(),c.getBankAccount(),c.getCity(),c.getCounty(),c.getFixedPhone(),
                                c.getLocation(),c.getMobilePhone(),c.getProvince(),c.getScore(),c.getCompanyIndex());
                        System.out.println(str);
                        bops.write(str);
                        bops.newLine();
                        bops.flush();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bis.close();
                bops.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
