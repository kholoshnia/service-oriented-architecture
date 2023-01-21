module.exports = {
  extends: ['stylelint-config-recommended-scss', 'stylelint-config-twbs-bootstrap'],
  plugins: ['stylelint-no-unsupported-browser-features'],
  rules: {
    'at-rule-empty-line-before': [
      'always',
      {
        except: ['blockless-after-same-name-blockless', 'first-nested'],
        ignore: ['after-comment'],
      },
    ],
    'comment-whitespace-inside': 'always',
    'declaration-empty-line-before': [
      'always',
      {
        except: ['after-declaration', 'first-nested'],
        ignore: ['after-comment', 'inside-single-line-block'],
      },
    ],
    'declaration-property-value-disallowed-list': null,
    'max-empty-lines': 2,
    'max-nesting-depth': 5,
    'no-invalid-double-slash-comments': true,
    'rule-empty-line-before': [
      'always-multi-line',
      {
        except: ['first-nested'],
        ignore: ['after-comment'],
      },
    ],
    'selector-class-pattern': [
      /^(?:(?:o|c|u|t|s|is|has|_|js|qa)-)?[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*(?:__[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*)?(?:--[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*)?(?:\\[.+\\])?$/i,
      { message: 'The class selector is expected to be BEM-style.' },
    ],
    'scss/at-extend-no-missing-placeholder': null,
    'scss/dollar-variable-empty-line-before': [
      'always',
      {
        except: ['after-dollar-variable', 'first-nested'],
        ignore: ['after-comment'],
      },
    ],
    'string-quotes': ['double'],
    'plugin/no-unsupported-browser-features': [true, { severity: 'warning' }],

    // TODO fix while refactoring and remove
    'no-empty-source': null,
    'selector-max-id': null,
    'font-weight-notation': null,
    'color-named': null,
    'declaration-no-important': null,
    'scss/selector-no-redundant-nesting-selector': null,
    'keyframes-name-pattern': null,
    'selector-class-pattern': null,
    'block-no-empty': null,
    'selector-max-compound-selectors': null,
    'selector-no-qualifying-type': null,
    'selector-max-type': null,
    'no-duplicate-selectors': null,
    'scss/dollar-variable-pattern': null,
    'no-duplicate-at-import-rules': null,
    'declaration-block-no-duplicate-properties': null,
    'font-family-no-duplicate-names': null
  },
};
