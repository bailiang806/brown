package com.happydriving.rockets.server.component.crontask;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.service.RegionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mazhiqiang
 */
@Component
@Deprecated
public class RegionGeneratorTask {

    private static final Logger LOG = Logger.getLogger(RegionGeneratorTask.class);

    @Autowired
    private RegionService regionService;

    public void execute() throws BusinessException {
        LOG.info("Start to generate region file...");
//        RegionGeneratorFileFormat regionGeneratorFileFormat = regionService.generateRegionsRelated();
//        String resultString = JsonPersistenceUtils.writeToString(regionGeneratorFileFormat);
//
//        File outputFolder = new File("/Users/mazhiqiang/Downloads/");
//        String fileName = "regionFile.json";
//        FileWriter fileWriter = null;
//        try {
//            fileWriter = new FileWriter(new File(outputFolder, fileName));
//            fileWriter.write(resultString);
//            LOG.info("Generate region file succeed!");
//        } catch (IOException e) {
//            throw new BusinessException(e);
//        } finally {
//            IOUtils.closeQuietly(fileWriter);
//        }

    }

}
