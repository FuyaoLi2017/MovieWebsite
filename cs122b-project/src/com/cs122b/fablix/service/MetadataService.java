package com.cs122b.fablix.service;

import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.DBTableVo;

public interface MetadataService {

	ResponseModel<List<DBTableVo>> showMetaData();

}
