package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Tag;
import org.springframework.stereotype.Service;

@Service
public interface TagService {
    Tag findOrCreateTag(String tagName);
}
