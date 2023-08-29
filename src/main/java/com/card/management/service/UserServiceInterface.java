package com.card.management.service;

import com.card.management.configuration.exception.APIResponse;

public interface UserServiceInterface {

    APIResponse findUserById(Long id);

    APIResponse findByPagenation(Long userId, Integer pageNo, Integer pageSize, String sortBy);
}
