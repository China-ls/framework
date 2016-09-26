package com.infinite.eoa.core.serivce;

import com.infinite.eoa.core.entity.Pager;
import com.infinite.eoa.core.persistent.IMorphiaDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

import java.util.List;

public abstract class AbstractPagerService<T> implements IPagerService<T> {

    @Override
    public Pager<T> listPager(int page, int size) {
        return listPager(page, size, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pager<T> listPager(int page, int size, Query<T> query) {
        if (page <= 1) {
            page = 1;
        }
        if (size < 0) {
            size = 10;
        } else if (size == 0) {
            size = 0;
        }
        Pager<T> pager = new Pager<T>();
        long total = 0;
        IMorphiaDAO morphiaDAO = getMorphiaDAO();
        if (null == query) {
            total = morphiaDAO.count();
            query = morphiaDAO.createQuery();
        } else {
            total = morphiaDAO.count(query);
        }
        pager.setTotal(total);
        if (size == 0) {
            pager.setTotalPage(1);
        } else {
            float totalPageFloat = total / (size * 1.0F);
            int totalPage = Math.round(totalPageFloat);
            if (totalPage < totalPageFloat) {
                totalPage = totalPage + 1;
            }
            pager.setTotalPage(totalPage);
        }
        if (total > 0) {
            int offset = (page - 1) * size;
            query.offset(offset);
            query.limit(size);
            QueryResults<T> result = morphiaDAO.find(query);
            List<T> list = result.asList();
            pager.setData(list);
            pager.setHasPre(offset > 0);
            pager.setHasNext((offset + list.size()) < total);
        }
        return pager;
    }

}
