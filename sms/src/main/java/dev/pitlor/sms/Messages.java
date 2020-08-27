package dev.pitlor.sms;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dev.pitlor.sms.repositories.MessageRepository;

public class Messages {
    private MessageRepository messageRepository;

    @Inject
    public Messages(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> readAllAfter(OffsetDateTime minimumTime) {
        List<List<String>> messageIds = messageRepository.getAllIdsAfter(minimumTime);

        List<Message> messages = new ArrayList<>();
        messages.addAll(messageIds.get(MessageRepository.SMS)
            .stream()
            .map(messageRepository::getSmsById)
            .map(Message::from)
            .collect(Collectors.toList()));
        messages.addAll(messageIds.get(MessageRepository.MMS)
            .stream()
            .map(messageRepository::getMmsById)
            .map(Message::from)
            .collect(Collectors.toList()));

        return messages;
    }
}
