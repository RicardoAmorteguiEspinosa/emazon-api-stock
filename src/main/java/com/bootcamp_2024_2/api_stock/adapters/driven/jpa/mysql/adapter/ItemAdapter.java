package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.mapper.IItemEntityMapper;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository.IItemRepository;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.spi.IItemPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemAdapter implements IItemPersistencePort {

    private final IItemRepository iItemRepository;
    private final IItemEntityMapper iItemEntityMapper;

    @Override
    public Item saveItem(Item item) {

        return iItemEntityMapper.toModel( iItemRepository.save(
                iItemEntityMapper.toEntity(item)));
    }
}