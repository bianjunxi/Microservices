package cn.itcast.hotel.service;

import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.pojo.RequestParams;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface IHotelService extends IService<Hotel> {
    PageResult search(RequestParams params);

    /**
     * 获取页面查询条件信息(城市、品牌、星级)
     * @return
     */
    Map<String, List<String> > filters(RequestParams params);

    /**
     * 新增或者修改文档
     * @param id
     */
    void insertById(Long id);

    /**
     * 删除文档
     * @param id
     */
    void deleteById(Long id);
}
