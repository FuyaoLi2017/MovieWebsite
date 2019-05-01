package com.cs122b.fablix.dao;

import java.util.List;

import com.cs122b.fablix.entity.vo.MetadataVo;

public interface MetadataDao {

	List<MetadataVo> selectMetadataByTable(String string);

}
