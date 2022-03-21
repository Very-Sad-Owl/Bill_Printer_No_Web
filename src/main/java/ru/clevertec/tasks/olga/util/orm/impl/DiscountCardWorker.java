package ru.clevertec.tasks.olga.util.orm.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.model.*;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class DiscountCardWorker extends NodeWorker<DiscountCard> {

    private static final NodeWorker<CardDiscountType> discTypeWorker = new DiscountTypeWorker();

    @Override
    public DiscountCard nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException {
        return DiscountCard.builder()
                .id(rs.getLong(!isJoinQuery ? "id" : "card_id"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .cardDiscountType(discTypeWorker.nodeToModel(rs, true))
                .build();
    }

    @Override
    public void modelToNode(DiscountCard model, PreparedStatement st) throws SQLException {

    }
}
