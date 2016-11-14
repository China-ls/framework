package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.entity.Pager;
import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.core.serivce.AbstractPagerService;
import com.infinite.eoa.entity.VirtualSensorWaterQuality;
import com.infinite.eoa.persistent.VirtualSensorWaterQualityDAO;
import com.infinite.eoa.service.VirtualSensorWaterQualityService;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author by hx on 16-7-26.
 */
@Service("VirtualSensorWaterQualityService")
public class VirtualSensorWaterQualityServiceImpl extends AbstractPagerService<VirtualSensorWaterQuality> implements VirtualSensorWaterQualityService {
    private static Logger LOG = LoggerFactory.getLogger(VirtualSensorWaterQualityServiceImpl.class);

    @Autowired
    private VirtualSensorWaterQualityDAO virtualSensorWaterQualityDAO;

    @Override
    public IMorphiaDAO getMorphiaDAO() {
        return virtualSensorWaterQualityDAO;
    }

    @Override
    public Pager<VirtualSensorWaterQuality> listPager(String sensor_id, int page, int size) {
        Query<VirtualSensorWaterQuality> query = virtualSensorWaterQualityDAO.createQuery();
        query.filter("sensor_id", sensor_id);
        query.order("-time");
        return listPager(page, size, query);
    }

    public VirtualSensorWaterQuality save(VirtualSensorWaterQuality quality) {
        if (null == quality) {
            return null;
        }
        quality.setTime(new Date(System.currentTimeMillis()));
        Key<VirtualSensorWaterQuality> key = virtualSensorWaterQualityDAO.save(quality);
        quality.setId((ObjectId) key.getId());
        return quality;
    }

}
