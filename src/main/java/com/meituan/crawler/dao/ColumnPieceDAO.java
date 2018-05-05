package com.meituan.crawler.dao;

import com.meituan.crawler.model.ColumnPiece;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/19
 * \* Time: 16:53
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Repository
public class ColumnPieceDAO {

    private static final String BASE_PACKAGE = "com.meituan.crawler.mapper.ColumnPiece.";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public void addColumnPiece(ColumnPiece columnPiece) {
        sqlSessionTemplate.insert(BASE_PACKAGE + "insert", columnPiece);
    }

    public List<ColumnPiece> listColumnPiece() {
        List<ColumnPiece> res = sqlSessionTemplate.selectList(BASE_PACKAGE + "select");
        return res;
    }
}
