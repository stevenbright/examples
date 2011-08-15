package com.truward.jdoblog.services.init;

import com.truward.jdoblog.services.service.BlogService;
import org.apache.log4j.Logger;
import org.springframework.dao.DataRetrievalFailureException;

/**
 * Sample data pool that is filled when initialization gets done
 */
public class SampleDataPool {
    private static Logger log = Logger.getLogger(SampleDataPool.class);

    public SampleDataPool(BlogService blogService) {
        try {

            // accounts
            blogService.saveAccount("hugh", "http://www.fotobank.ru/img/R042-0175.jpg?size=m");
            blogService.saveAccount("ivan", "http://www.fotobank.ru/img/FB19-1094.jpg?size=m");
            blogService.saveAccount("jenny", "http://www.fotobank.ru/img/BY03-5858.jpg?size=m");
            blogService.saveAccount("rachel", "http://www.fotobank.ru/img/BY03-5870.jpg?size=m");
            blogService.saveAccount("anonymous", "http://www.fotobank.ru/img/GS01-4184.jpg?size=m");


        } catch (DataRetrievalFailureException exception) {
            log.info("Initialization stopped due to existing data in the pool", exception);
        } catch (Throwable throwable) {
            log.info("Initialization failure", throwable);
        }
    }
}
