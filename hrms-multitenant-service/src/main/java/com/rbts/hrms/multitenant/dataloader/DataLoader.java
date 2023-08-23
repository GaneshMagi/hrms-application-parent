package com.rbts.hrms.multitenant.dataloader;






import com.rbts.hrms.multitenant.entity.Range;
import com.rbts.hrms.multitenant.repository.RangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {



    @Autowired
    RangeRepository rangeRepository;



    @Override
    public void run(String... args) throws Exception {

        Range role1= new Range();
        String data="1-50";
        Range r=rangeRepository.findByName(data);

        if(r!=null)
        {

        }else
        {
            role1.setPreference(1);
            role1.setEmpRange(data);
            rangeRepository.save(role1);
        }

        Range role2= new Range();
        String data1="50-200";
        Range r1=rangeRepository.findByName(data1);
        if(r1!=null)
        {

        }else
        {
            role2.setPreference(2);
            role2.setEmpRange(data1);
            rangeRepository.save(role2);
        }



        Range role3=new Range();
        String data2="200-400";
        Range r3=rangeRepository.findByName(data2);
        if(r3!=null)
        {

        }else
        {
            role3.setPreference(3);
            role3.setEmpRange(data2);
            rangeRepository.save(role3);
        }



        Range role4=new Range();
        String data3="1000-above";
        Range r4=rangeRepository.findByName(data3);
        if(r4!=null)
        {

        }else
        {
            role4.setPreference(7);
            role4.setEmpRange(data3);
            rangeRepository.save(role4);
        }


        Range role6=new Range();
        String data5="400-600";
        Range r6=rangeRepository.findByName(data5);
        if(r6!=null)
        {

        }else
        {
            role6.setPreference(4);
            role6.setEmpRange(data5);
            rangeRepository.save(role6);
        }


        Range role7=new Range();
        String data7="600-800";
        Range r7=rangeRepository.findByName(data7);
        if(r7!=null)
        {

        }else
        {
            role7.setPreference(5);
            role7.setEmpRange(data7);
            rangeRepository.save(role7);
        }




        Range role5=new Range();
        String data4="800-1000";
        Range r5=rangeRepository.findByName(data4);
        if(r5!=null)
        {

        }else
        {
            role5.setPreference(6);
            role5.setEmpRange(data4);
            rangeRepository.save(role5);
        }




    }
}
