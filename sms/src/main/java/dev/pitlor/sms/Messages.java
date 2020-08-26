package dev.pitlor.sms;

import android.content.Context;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.pitlor.sms.repositories.MessageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Messages {
    @NonNull private Context context;

    public List<Message> readAllAfter(OffsetDateTime minimumTime) {
        MessageRepository messageRepository = new MessageRepository(context);
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
