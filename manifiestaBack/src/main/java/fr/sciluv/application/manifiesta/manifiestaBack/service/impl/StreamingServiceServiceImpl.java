package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.StreamingService;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.StreamingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamingServiceServiceImpl implements StreamingServiceService {

    @Autowired
    StreamingServiceRepository StreamingServiceRepository;
    @Override
    public StreamingService findByName(String name) {
        return StreamingServiceRepository.findByName(name);
    }
}
