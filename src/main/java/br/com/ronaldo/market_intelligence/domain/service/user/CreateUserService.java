package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.domain.model.CreateUserRequestModel;
import br.com.ronaldo.market_intelligence.domain.model.CreateUserResponseModel;

public interface CreateUserService {
    CreateUserResponseModel execute(CreateUserRequestModel createUserRequestModel);
}
