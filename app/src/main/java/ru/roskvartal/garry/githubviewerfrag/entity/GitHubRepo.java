package ru.roskvartal.garry.githubviewerfrag.entity;

import ru.roskvartal.garry.githubviewerfrag.R;
import android.support.annotation.Nullable;

public class GitHubRepo {

    //  Основные реквизиты репозитория.

    private int    repoId;              //  ID - число (id).
    private String repoName;            //  Краткое название репозитория (name).
    private String repoFullName;        //  Полное название репозитория (full_name).
    private String repoDesc;            //  Краткое описание репозитория (description).
    private String repoUrl;             //  Ссылка на репозиторий (html_url).
    private String ownerName;           //  Имя владельца (owner:login).
    private String ownerAvatarUrl;      //  Аватар владельца (owner:avatar_url).

    //  Детальные реквизиты репозитория.

    private String mainLang;            //  Основной язык кода (language).
    private int    starsCount;          //  Количество звезд (stargazers_count).
    private int    watchersCount;       //  Количество следящих (watchers_count).
    private int    forksCount;          //  Количество ответвлений (forks_count).
    private int    issuesCount;         //  Количество открытых заявок (open_issues_count).

    //  TEST
    private int ownerAvatarId;          //  Ссылка на ресурс с картинкой.


    public int getRepoId() { return repoId; }
    public void setRepoId(int repoId) { this.repoId = repoId; }

    @Nullable
    public String getRepoName() { return repoName; }
    public void setRepoName(String repoName) { this.repoName = repoName; }

    @Nullable
    public String getRepoFullName() { return repoFullName; }
    public void setRepoFullName(String repoFullName) { this.repoFullName = repoFullName; }

    @Nullable
    public String getRepoDesc() { return repoDesc; }
    public void setRepoDesc(String repoDesc) { this.repoDesc = repoDesc; }

    @Nullable
    public String getRepoUrl() { return repoUrl; }
    public void setRepoUrl(String repoUrl) { this.repoUrl = repoUrl; }

    @Nullable
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    @Nullable
    public String getOwnerAvatarUrl() { return ownerAvatarUrl; }
    public void setOwnerAvatarUrl(String ownerAvatarUrl) { this.ownerAvatarUrl = ownerAvatarUrl; }

    @Nullable
    public String getMainLang() { return mainLang; }
    public void setMainLang(String mainLang) { this.mainLang = mainLang; }

    public int getStarsCount() { return starsCount; }
    public void setStarsCount(int starsCount) { this.starsCount = starsCount; }

    public int getWatchersCount() { return watchersCount; }
    public void setWatchersCount(int watchersCount) { this.watchersCount = watchersCount; }

    public int getForksCount() { return forksCount; }
    public void setForksCount(int forksCount) { this.forksCount = forksCount; }

    public int getIssuesCount() { return issuesCount; }
    public void setIssuesCount(int issuesCount) { this.issuesCount = issuesCount; }

    //  TEST
    public int getOwnerAvatarId() { return ownerAvatarId; }
    public void setOwnerAvatarId(int ownerAvatarId) { this.ownerAvatarId = ownerAvatarId; }


    //  TEST
    private GitHubRepo(
            int repoId, String repoName, String repoDesc, String repoUrl, String ownerName, int ownerAvatarId,
            String mainLang, int starsCount, int watchersCount, int forksCount, int issuesCount) {

        this.repoId = repoId;
        this.repoName = repoName;
        this.repoDesc = repoDesc;
        this.repoUrl = repoUrl;
        this.ownerName = ownerName;
        this.ownerAvatarId = ownerAvatarId;
        this.mainLang = mainLang;
        this.starsCount = starsCount;
        this.watchersCount = watchersCount;
        this.forksCount = forksCount;
        this.issuesCount = issuesCount;
    }

    //  TEST Этот метод используется адаптером при заполнении обычного String List.
    @Override
    public String toString() {
        return getOwnerName() + ": " + getRepoName();
    }

    //  TEST
    public static final GitHubRepo[] repos = {
            new GitHubRepo(
                    1, "babel-codemod",
                    "babel-codemod rewrites JavaScript and TypeScript using babel plugins.",
                    "https://github.com/square/babel-codemod",
                    "Square", R.drawable.a01, "TypeScript",
                    153, 35, 8, 29
            ),
            new GitHubRepo(
                    2, "misk",
                    null,
                    "https://github.com/square/misk",
                    "Square", R.drawable.a02, "Kotlin",
                    106, 23, 11, 31
            ),
            new GitHubRepo(
                    3, "spincycle",
                    "An orchestration framework that makes it easy to manage the complexity of highly available, persistent data stores",
                    "https://github.com/square/spincycle",
                    "Square", R.drawable.a03, "Go",
                    26, 5, 6, 89
            ),
            new GitHubRepo(
                    4, "wire",
                    "Clean, lightweight protocol buffers for Android and Java.",
                    "https://github.com/square/wire",
                    "Square", R.drawable.a04, "Java",
                    2541, 339, 173, 201
            ),
            new GitHubRepo(
                    5, "sqldelight",
                    "Generates Java models from CREATE TABLE statements.",
                    "https://github.com/square/sqldelight",
                    "Square", R.drawable.a05, "Kotlin",
                    1789, 456, 234, 12
            ),
            new GitHubRepo(
                    6, "okhttp",
                    "An HTTP+HTTP/2 client for Android and Java applications.",
                    "https://github.com/square/okhttp",
                    "Square", R.drawable.a06, "Java",
                    28896, 1648, 6627, 164
            ),
            new GitHubRepo(
                    7, "lgtm",
                    "Simple object validation for JavaScript.",
                    "https://github.com/square/lgtm",
                    "Square", R.drawable.a07, "JavaScript",
                    348, 31, 9, 15
            ),
            new GitHubRepo(
                    8, "moshi",
                    "A modern JSON library for Android and Java.",
                    "https://github.com/square/moshi",
                    "Square", R.drawable.a08, "Java",
                    4356, 128, 47, 81
            ),
            new GitHubRepo(
                    9, "retrofit",
                    "Type-safe HTTP client for Android and Java by Square, Inc.",
                    "https://github.com/square/retrofit",
                    "Square", R.drawable.a09, " Java",
                    29653, 1611, 5679, 60
            ),
            new GitHubRepo(
                    10, "okio",
                    "A modern I/O API for Java",
                    "https://github.com/square/okio",
                    "Square", R.drawable.a10, "Kotlin",
                    5519, 178, 834, 123
            ),
            new GitHubRepo(
                    11, "shuttle",
                    "String extraction, translation and export tools for the 21st century. \"Moving strings around so you don't have to\"",
                    "https://github.com/square/shuttle",
                    "Square", R.drawable.a11, "Ruby",
                    660, 135, 97, 12
            ),
            new GitHubRepo(
                    12, "debug_socket",
                    "Debug socket.",
                    "https://github.com/square/debug_socket",
                    "Square", R.drawable.a12, "Ruby",
                    2, 3, 4, 1
            ),
            new GitHubRepo(
                    13, "connect-api-examples",
                    null,
                    "https://github.com/square/connect-api-examples",
                    "Square", R.drawable.a13, " JavaScript",
                    100, 100, 100, 100
            ),
            new GitHubRepo(
                    14, "p2",
                    "Platypus Platform: Tools for Scalable Deployment",
                    "https://github.com/square/p2",
                    "Square", R.drawable.a14, "Go",
                    10, 10, 10, 10
            ),
            new GitHubRepo(
                    15, "picasso",
                    "A powerful image downloading and caching library for Android",
                    "https://github.com/square/picasso",
                    "Square", R.drawable.a15, "Java",
                    15999, 969, 3896, 143
            ),
            new GitHubRepo(
                    16, "github / gitignore",
                    "A collection of useful .gitignore templates",
                    "https://github.com/github/gitignore",
                    "Git", R.drawable.a16, null,
                    73000, 2677, 34013, 39
            ),
            new GitHubRepo(
                    17, "so-fancy / diff-so-fancy",
                    "Good-lookin' diffs. Actually… nah… The best-lookin' diffs.",
                    "https://github.com/so-fancy/diff-so-fancy",
                    "Git", R.drawable.a17, "Perl",
                    10800, 223, 123, 16
            ),
            new GitHubRepo(
                    18, "geeeeeeeeek / git-recipes",
                    "Git recipes in Chinese by Zhongyi Tong. 高质量的Git中文教程.",
                    "https://github.com/geeeeeeeeek/git-recipes",
                    "Git", R.drawable.a18, null,
                    45, 9, 67, 4
            ),
            new GitHubRepo(
                    19, "sameersbn / docker-gitlab",
                    "Dockerized GitLab",
                    "https://github.com/sameersbn/docker-gitlab",
                    "Git", R.drawable.a19, "Shell",
                    56, 35, 81, 1
            ),
            new GitHubRepo(
                    20, "git",
                    "Git Source Code Mirror - This is a publish-only repository and all pull requests are ignored. Please follow Documentation/SubmittingPatches procedure for any of your improvements.",
                    "https://github.com/git/git",
                    "Git", R.drawable.a20, "C",
                    24181, 2005, 14035, 0
            )
    };

}
