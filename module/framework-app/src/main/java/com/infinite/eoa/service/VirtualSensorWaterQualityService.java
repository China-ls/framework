package com.infinite.eoa.service;

import com.infinite.eoa.core.entity.Pager;
import com.infinite.eoa.core.serivce.IPagerService;
import com.infinite.eoa.entity.VirtualSensorWaterQuality;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface VirtualSensorWaterQualityService extends IPagerService<VirtualSensorWaterQuality> {

    public Pager<VirtualSensorWaterQuality> listPager(String sensor_id, int page, int size);

    public VirtualSensorWaterQuality save(VirtualSensorWaterQuality quality);

}
