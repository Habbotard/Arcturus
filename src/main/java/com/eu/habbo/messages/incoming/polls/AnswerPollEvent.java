package com.eu.habbo.messages.incoming.polls;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.polls.Poll;
import com.eu.habbo.habbohotel.polls.PollManager;
import com.eu.habbo.habbohotel.users.HabboBadge;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.generic.alerts.WiredRewardAlertComposer;
import com.eu.habbo.messages.outgoing.users.AddUserBadgeComposer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AnswerPollEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        int pollId = this.packet.readInt();
        int questionId = this.packet.readInt();
        int count = this.packet.readInt();

        String answer = "";
        for(int i = 0; i < count; i++)
        {
            answer += ":" + this.packet.readString();
        }

        answer = answer.substring(1);

        Poll poll = PollManager.getPoll(pollId);

        if(poll != null)
        {
            try
            {
                PreparedStatement statement = Emulator.getDatabase().prepare("INSERT INTO polls_answers(poll_id, user_id, question_id, answer) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE answer=VALUES(answer)");
                statement.setInt(1, pollId);
                statement.setInt(2, this.client.getHabbo().getHabboInfo().getId());
                statement.setInt(3, questionId);
                statement.setString(4, answer);
                statement.execute();
                statement.close();
                statement.getConnection().close();
            }
            catch (SQLException e)
            {
                Emulator.getLogging().logSQLException(e);
            }

            if(poll.getQuestions().get(poll.getQuestions().size()).getId() == questionId)
            {
                if(poll.getBadgeReward().length() > 0)
                {
                    if(this.client.getHabbo().getHabboInventory().getBadgesComponent().getBadge(poll.getBadgeReward()) == null)
                    {
                        HabboBadge badge = new HabboBadge(0, poll.getBadgeReward(), 0, this.client.getHabbo());
                        Emulator.getThreading().run(badge);
                        this.client.getHabbo().getHabboInventory().getBadgesComponent().addBadge(badge);
                        this.client.sendResponse(new AddUserBadgeComposer(badge));
                        this.client.sendResponse(new WiredRewardAlertComposer(WiredRewardAlertComposer.REWARD_RECEIVED_BADGE));
                    }
                    else
                    {
                        this.client.sendResponse(new WiredRewardAlertComposer(WiredRewardAlertComposer.REWARD_ALREADY_RECEIVED));
                    }
                }
            }
        }
    }
}
