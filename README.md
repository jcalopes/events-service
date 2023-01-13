## Micronaut 3.8.0 Documentation

- [User Guide](https://docs.micronaut.io/3.8.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.8.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.8.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---
### Bumping Version

Everytime a new integration comes into the master branch the pipeline uses an GithubAction to bump a new version based 
on the commit structure which follows semantic-versioning convention.

By default semantic-release uses [Angular Commit Message Conventions](https://github.com/angular/angular.js/blob/master/DEVELOPERS.md#-git-commit-guidelines).

Here is an example of the release type that will be done based on a commit messages:

<table>
<tr>
<td> Commit message </td> <td> Release type </td>
</tr>
<tr>
<td>

```
fix(pencil): stop graphite breaking when too much pressure applied
```

</td>
<td>Patch Release</td>
</tr>
<tr>
<td>

```
feat(pencil): add 'graphiteWidth' option
```

</td>
<td>Minor Release</td>
</tr>
<tr>
<td>

```
perf(pencil): remove graphiteWidth option

BREAKING CHANGE: The graphiteWidth option has been removed.
The default graphite width of 10mm is always used for performance reasons.
```

</td>
<td>Major Release</td>
</tr>
</table>

If no commit message contains any information, then **default_bump** will be used.

Retired from https://github.com/mathieudutour/github-tag-action owner of Github Action used for bumping versions.