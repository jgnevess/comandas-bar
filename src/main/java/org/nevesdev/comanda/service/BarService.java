package org.nevesdev.comanda.service;

import org.nevesdev.comanda.exceptions.BarException;
import org.nevesdev.comanda.model.bar.Bar;
import org.nevesdev.comanda.repository.BarRepository;
import org.nevesdev.comanda.repository.UserRepository;
import org.nevesdev.comanda.service.interfaces.BarServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarService implements BarServiceInterface {

    @Autowired
    private BarRepository barRepository;

    @Override
    public Bar findBarById(Long id) {
        return barRepository.findById(id).orElseThrow(() -> new BarException("Bar não encontrado", 404));
    }
}
