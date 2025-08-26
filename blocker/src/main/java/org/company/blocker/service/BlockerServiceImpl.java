package org.company.blocker.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BlockerServiceImpl implements BlockerService{

    @Override
    public Boolean isSuspicious() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
