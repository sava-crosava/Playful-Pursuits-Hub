package com.sava.playful.pursuits.hub.playfulpursuitshub.service.impl;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Tag;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TagServiceImpl {
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    //todo delete this and his interface
    public Tag findOrCreateTag(String tagName) {
        Tag existingTag = tagRepository.findByTagName(tagName);

        if (existingTag != null) {
            // Категорія вже існує, повертаємо її
            return existingTag;
        } else {
            // Категорія не існує, створюємо нову
            Tag newTag = new Tag();
            newTag.setTagName(tagName);
            tagRepository.save(newTag);
            return newTag;
        }
    }
}
