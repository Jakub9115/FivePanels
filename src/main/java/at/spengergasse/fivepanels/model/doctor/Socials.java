package at.spengergasse.fivepanels.model.doctor;

import at.spengergasse.fivepanels.model.messenger.Chat;
//import repository.ChatRepository;

import java.util.*;

import static at.spengergasse.fivepanels.foundation.Assert.*;

public class Socials {

    // not null
    private Map<UUID, Relation> relation;

    public Socials() {
        this.relation = new HashMap<>();
    }

    public Map<UUID, Relation> getRelation() {
        return relation;
    }


    // todo methods in service
    public void addFriend(Doctor doctor, Doctor doctorToAdd) {
        isNotNull(doctorToAdd, "userToAdd");
        isNotNull(doctor, "user");

        if (doctor.equals(doctorToAdd))
            throw new UserException(STR."addFriend(): User can not add himself");

        // if one user added another user and the other user tries to add him back, they become friends
        if (relation.get(doctorToAdd.getId()) == Relation.INCOMING) {
            acceptFriendRequest(doctor, doctorToAdd);
            return;
        }

        if (relation.containsKey(doctorToAdd.getId()))
            throw new UserException(STR."addFriend(): User already has a Relation with \{ doctorToAdd }");

        relation.put(doctorToAdd.getId(), Relation.OUTGOING);
        doctorToAdd.getSocials().relation.put(doctor.getId(), Relation.INCOMING);
    }

    public void acceptFriendRequest(Doctor doctor, Doctor doctorToAccept) {
        isNotNull(doctorToAccept, "userToAcceptRequest");
        isNotNull(doctor, "user");

        if (doctor.equals(doctorToAccept))
            throw new UserException(STR."acceptFriendRequest(): User can not accept a friend request from himself");

        if (!(relation.get(doctorToAccept.getId()) == Relation.INCOMING))
            throw new UserException(STR."acceptFriendRequest(): User does not have an incoming friend request from \{ doctorToAccept }");

        relation.put(doctorToAccept.getId(), Relation.FRIENDS);
        doctorToAccept.getSocials().relation.put(doctor.getId(), Relation.FRIENDS);
        Chat chat = new Chat(null, Set.of(doctorToAccept.getId(), doctor.getId()), false);
        doctor.getChats().add(chat);
        doctorToAccept.getChats().add(chat);
        ChatRepository.save(chat);
    }

    public void denyFriendRequest(Doctor doctor, Doctor doctorToDeny) {
        isNotNull(doctorToDeny, "userToDeny");
        isNotNull(doctor, "user");

        if (doctor.equals(doctorToDeny))
            throw new UserException(STR."denyFriendRequest(): User can not deny a friend request from himself");

        if (!(relation.get(doctorToDeny.getId()) == Relation.INCOMING))
            throw new UserException(STR."denyFriendRequest(): User does not have an incoming friend request from \{ doctorToDeny }");

        relation.remove(doctorToDeny.getId());
        doctorToDeny.getSocials().relation.remove(doctor.getId());
    }

    /**
     * Removes a friend from a user's friend list.
     * Throws an exception if the user tries to remove themselves or if they are not friends with the specified user.
     *
     * @param doctor         the user performing the removal
     * @param doctorToRemove the user to be removed from the friend list
     * @throws UserException if the user tries to remove themselves or if they are not friends with the specified user
     */
    public void removeFriend(Doctor doctor, Doctor doctorToRemove) {
        isNotNull(doctor, "user");
        isNotNull(doctorToRemove, "userToRemove");

        if (doctor.equals(doctorToRemove))
            throw new UserException(STR."removeFriend(): User can not remove himself");

        if (!(relation.get(doctorToRemove.getId()) == Relation.FRIENDS))
            throw new UserException(STR."removeFriend(): User is not friends with \{ doctorToRemove }");
        relation.remove(doctorToRemove.getId());
        doctorToRemove.getSocials().getRelation().remove(doctor.getId());

        // Deletes the chat between the 2 Users
        doctor.getChats().stream().filter(chat ->
                chat.getMembers().contains(doctorToRemove.getId()) && !chat.isGroupChat()).forEach(chat -> {
            doctor.getChats().remove(chat);
            doctorToRemove.getChats().remove(chat);
            ChatRepository.deleteById(chat.getId());
        });
    }

    private List<UUID> listRelation(Relation relation) {
        return this.relation.entrySet().stream().filter(uuidRelationEntry -> uuidRelationEntry.getValue().equals(relation))
                .map(Map.Entry::getKey).toList();
    }

    public List<UUID> getIncomingFriendRequests() {
        return listRelation(Relation.INCOMING);
    }

    public List<UUID> getFriends() {
        return listRelation(Relation.FRIENDS);
    }

    public List<UUID> getOutgoingFriendRequests() {
        return listRelation(Relation.OUTGOING);
    }
}
