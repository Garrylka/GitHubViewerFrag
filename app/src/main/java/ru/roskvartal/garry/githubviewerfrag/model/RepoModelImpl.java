package ru.roskvartal.garry.githubviewerfrag.model;

import android.support.annotation.Nullable;

import ru.roskvartal.garry.githubviewerfrag.entity.GitHubRepo;


//  Переход на MVP.
public class RepoModelImpl implements RepoModel {

    @Override
    public GitHubRepo[] getRepos() {
        return GitHubRepo.repos;
    }

    @Override
    public GitHubRepo getRepoByPos(int position) {
        return GitHubRepo.repos[position];
    }

    @Nullable
    @Override
    public GitHubRepo getRepoById(int repoId) {
        for (GitHubRepo r: GitHubRepo.repos) {
            if (r.getRepoId() == repoId) {
                return r;
            }
        }
        return null;
    }

    @Override
    public int getReposCount() {
        return GitHubRepo.repos.length;
    }
}
