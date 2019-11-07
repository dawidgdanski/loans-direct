package loans.direct.users;

import loans.direct.UserId;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class UsersService {

    private final Map<UserId, String> names;

    public String getName(UserId userId) {
        return names.get(userId);
    }

    public UserId logged() {
        return new UserId(0);
    }
}
