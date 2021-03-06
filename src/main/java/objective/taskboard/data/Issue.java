package objective.taskboard.data;

/*-
 * [LICENSE]
 * Taskboard
 * - - -
 * Copyright (C) 2015 - 2016 Objective Solutions
 * - - -
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * [/LICENSE]
 */

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import objective.taskboard.Constants;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int TASK = Constants.ISSUETYPE_ID_TASK;

    private static final int OS = Constants.ISSUETYPE_ID_OS;

    private static final int BUG = Constants.ISSUETYPE_ID_BUG;

    private String issueKey;

    private String projectKey;

    private String project;
    
	private long type;
	
    private String typeIconUri;

    private String summary;

    private long status;

    private String subresponsavel1;

    private String subresponsavel2;

    private String parent;

    private long parentType;
    
    private String parentTypeIconUri;

    private List<String> requires;

    private boolean render;

    private boolean favorite;

    private boolean hidden;

    private String color;

    private String subResponsaveis;

    private String assignee;
    
    private String usersInvalidTeam;

    private long priority;

    private String estimativa;

    @JsonDeserialize(using = DateDeserializer.class)
    private Date dueDate;

    private String description;

    private List<String> teams;

    private String comments;

    @JsonProperty(access = Access.WRITE_ONLY)
    private Map<String, Object> customFields;

    public static Issue from(String issueKey, String projectKey, String project, long issueType, String typeIconUri, String summary, long status, String subresponsavel1,
                             String subresponsavel2, String parent, long parentType, String parentTypeIconUri, List<String> requires, String subResponsaveis, String assignee,
                             String usersInvalidTeam, long priority, String estimativa, Date dueDate, String description, List<String> teams, String comments, Map<String, Object> customFields) {
        String color = setIssueColor(issueType, parentType);
        return new Issue(issueKey, projectKey, project, issueType, typeIconUri, summary, status, subresponsavel1, subresponsavel2,
                parent, parentType, parentTypeIconUri, requires, false, false, false, color, subResponsaveis, assignee, usersInvalidTeam, priority, estimativa, dueDate, description, teams, comments, customFields);
    }

    /**
     * Subtasks only need to show their key and summary.
     */
    public static Issue from(String issueKey, String summary) {
        Issue issue = new Issue();
        issue.setIssueKey(issueKey);
        issue.setSummary(summary);
        return issue;
    }

    private static String setIssueColor(long type, long parentType) {
        String color = "#ddf9d9";
        if (type == OS || parentType == OS) {
            color = "#add9fe";
        } else if (type == TASK || parentType == TASK) {
            color = "#fee5bc";
        } else if (type == BUG || parentType == BUG) {
            color = "#FF8B94";
        }
        return color;
    }

    @JsonAnyGetter
    public Map<String, Object> getCustomFields() {
        return customFields;
    }

}
