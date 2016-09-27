package com.infinite.eoa.core.serivce;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.core.entity.Pager;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

public interface IPagerService<T> {

    public Pager<T> listPager(int page, int size);

    public Pager<T> listPager(int page, int size, Query<T> query);

    public IMorphiaDAO getMorphiaDAO();

    public Key insert(T t);

}
